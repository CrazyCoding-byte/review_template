package src

import (
	"fmt"
	"os"
)

func demo() {
	file, err := os.OpenFile("demo.txt", os.O_RDWR, 0666)
	if err != nil {
		fmt.Println("打开文件失败:", err)
		return
	}
	defer file.Close()
	buff := make([]byte, 1024)
	file.Read(buff)
	fmt.Println(string(buff))
}
