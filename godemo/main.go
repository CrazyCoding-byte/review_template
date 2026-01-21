package main

import (
	"bufio"
	"errors"
	"fmt"
	"io"
	"math"
	"os"
	"strconv"
	"strings"
	"sync"
	"time"
	"unicode/utf8"
)

var (
	count int
	mutex sync.Mutex //互斥锁
	wg    sync.WaitGroup
)

type MyInt int
type User struct {
	Name string
	Age  int
}

type Person struct {
	Name string
	Age  int
}

// import (e exmaple
// _ "mysql_driver_go" _是匿名导入
// ) e是起的别名
const Myname = "jack"   //名称大写为公有
const mySalary = "1000" //名称小写为私有
func filedemo() {
	//使用包 e.xx
	num := 1
	num++
}
func transform() {
	//类型转换
	var a int = 10
	var b float64 = float64(a) //int转float64
	b1 := b
	fmt.Println(b1)
	var floatNum = 3.99
	intNum2 := float64(floatNum)
	fmt.Println(intNum2)
	// 3. 布尔类型不能直接转数值（编译报错）
	// var b bool = true
	// var num int = int(b) // 错误：cannot convert b (type bool) to type int

	//字符串和数字转换
	str := "123"
	strNum, err := strconv.Atoi(str) //Atoi=>Ascii to int
	if err != nil {
		fmt.Println("转换失败:", err)
	} else {
		fmt.Println("转换成功:", strNum)
	}

	//整数转字符串
	num3 := 123
	strNum3 := strconv.Itoa(num3)
	fmt.Println(strNum3)

	//字符串转浮点数
	str4 := "3.14"
	floatNum1, err := strconv.ParseFloat(str4, 64) //64表示转换为float64
	if err != nil {
		fmt.Println("转换失败:", err)
	} else {
		fmt.Println("转换成功:", floatNum1)
	}
	//浮点数转字符串
	floatNum2 := 3.14
	str6 := strconv.FormatFloat(floatNum2, 'f', 2, 64) //'f'表示转换为字符串，2表示小数点后2位，64表示转换为float64
	fmt.Println(str6)

	//自定义类型转换
	var myInt MyInt = 10
	var normalInt int = int(myInt) //MyInt->int
	fmt.Println(normalInt)
	var normalInt2 int = 200
	var mi2 MyInt = MyInt(normalInt2) //int->MyInt
	fmt.Println("int 200->MyInt:", mi2)

	//结构体转换
	u := User{Name: "李四", Age: 18}
	p := Person(u)

	fmt.Println(p)
}

// 操作string
func string2() {
	//1.长度相关
	s := "Hello,go语言"
	fmt.Println(len(s))                    //打印字符串长度
	fmt.Println(utf8.RuneCountInString(s)) //打印字符串真实字节长度
	//2.拼接
	s1 := "Hello"
	s2 := "World"
	s3 := s1 + " " + s2 //基础拼接少量字符串用
	//多字符串
	words := []string{"go", "python", "java", "c++", "Rust"}
	var sb strings.Builder
	for _, word := range words {
		sb.WriteString(word)
		sb.WriteString("|")
	}
	//获取最终的结果
	s4 := sb.String()
	fmt.Println(s3)
	fmt.Println(s4)

	//3.截取(切片语法)
	sub1 := s[0:5] //取0~4索引: Hello
	sub2 := s[7:]  //取7~最后: go语言
	sub3 := s[:]   //取全部: Hello,go语言
	fmt.Println(sub1, sub2, sub3)
	//4.遍历
	for i := 0; i < len(s); i++ {
		fmt.Println(s[i])
	}
	for i, ch := range s {
		fmt.Println(i, ch)
	}
	for _, ch := range s {
		fmt.Println(ch)
	}
	//5.分割
	splitstr := "a,b,c,d"
	parts := strings.Split(splitstr, ",")
	fmt.Println(parts)
	//6.替换
	replaceStr := "Hello go ,go isGood"
	//替换所有go
	repl := strings.ReplaceAll(replaceStr, "go", "golang")
	//只替换第一个go
	rep2 := strings.Replace(replaceStr, "go", "golang", 1)
	fmt.Println(repl, rep2)
	//6.大小写转换
	upper := strings.ToUpper("hello")
	lower := strings.ToLower("HELLO")
	fmt.Println(upper, lower)
	//7.包含/前缀/后缀判断
	fmt.Println("包含'go'", strings.Contains(s, "go"))
	fmt.Println("以'Hello'开头", strings.HasPrefix(s, "Hello")) //true
	fmt.Println("以'go'结尾", strings.HasSuffix(s, "go"))       //true
	//8.去除空格/指定字符
	trimStr := "hello go"
	trimSpace := strings.TrimSpace(trimStr) //去除收尾空格
	trimChar := strings.Trim(trimStr, " h") //去除首位的空格和'h'
	fmt.Println("去空格:", trimSpace)          //hello go
	fmt.Println("去首尾'h'", trimChar)

	//9.字符串转换为字节数组
	str := "hello go"
	byteSlice := []byte(str) //转字节切片
	runSlice := []rune(str)  //转字符切片(处理中文)
	byte1 := string(byteSlice)
	fmt.Println("字符切片:", byteSlice)
	fmt.Println("字符切片:", runSlice)
	fmt.Println("字节切片:", byte1)

	//10.进制转换
	num := 255
	bin := strconv.FormatInt(int64(num), 2)  //二进制
	oct := strconv.FormatInt(int64(num), 8)  //八进制
	hex := strconv.FormatInt(int64(num), 16) //十六进制
	fmt.Println(bin, oct, hex)
	//浮点数精度/比较
	f1 := 0.1 + 0.2
	f2 := 0.3
	//浮点数不能直接用==比较,要判断差值是否小于极小值
	if math.Abs(f1-f2) < 1e-9 {
		fmt.Println("相等")
	} else {
		fmt.Println("不相等")
	}
}
func stltest() {
	//初始化切片
	//1.方式1空切片
	var nums []int
	//尾部添加
	nums = append(nums, 1, 2, 3, 4, 5)
	fmt.Println(nums)
	//2.方式2带初始值
	fruits := []string{"apple", "banana", "orange"}
	fruits = append(fruits, "2", "42", "1")

	var nums1 [5]int
	for i, l := range nums1 {
		nums1[i] = l
	}
	//删除第一个元素  说白了就是取1~最后一个元素
	fruits = fruits[1:]

	//删除最后一个元素 说白就是取0~倒数第二个元素
	fruits = fruits[:len(fruits)-1]

	//清空切片
	fruits = fruits[:0]
	fruits = nil

	//修改元素
	fruits[0] = "1" //根据索引去修改

	//初始化map
	var useMap map[string]int
	useMap = make(map[string]int, 10)
	//增
	useMap["jack"] = 100
	//方式二
	useInfo := map[string]string{"name": "jack", "age": "18"}
	//改
	useInfo["name"] = "wangwu"
	//删
	delete(useInfo, "name")
	userName := useInfo["name"]
	fmt.Printf("userName:%s", userName)

}

/*
*

		io接口是go io流的顶级接口
		io.Reader(p []byte)(n int, err error) 从数据源读取数据
		io.Writer(p []byte)(n int, err error)向数据源写入数据
		io.Closer()(err error) 关闭数据源
		io.ReaderAt(p []byte, off int64)(n int, err error) 从指定位置读取数据
		io.ReadWriter 嵌入Reader+Writer 同时具备读和写能力的接口
		io.ReadCloser 嵌入Reader+Closer 同时具备读和关闭数据的接口
		io.WriteCloser 嵌入Writer+Closer 同时具备写和关闭数据的接口


	   关键实现 os bufio bytes
	   os.open 以只读模式打开文件
	   os.Create 创建文件
	   os.openfile 通用打开文件("指定模式/权限")
*/
func ioTest() {
	filePath := "dtest.txt"
	//创建/打开文件:o_create(创建)
	file, err := os.OpenFile(filePath, os.O_RDWR|os.O_CREATE, 0666)
	if err != nil {
		fmt.Printf("打开文件失败")
		return
	}
	defer file.Close() //延迟关闭文件,避免资源泄露

	//写入字节流
	writeData := []byte("Hello,go语言")
	n, nil := file.Write(writeData)
	if err != nil {
		fmt.Println("写入失败:", err)
		return
	}
	fmt.Println("写入字节数:", n)

	file, err = os.Open(filePath) //只读打开
	if err != nil {
		fmt.Println("打开文件失败:", err)
		return
	}
	defer file.Close()
	buff := make([]byte, 1024) //创建一个字节切片
	var readData []byte
	for {
		n, err := file.Read(buff)
		if n > 0 {
			readData = append(readData, buff[:n]...) //拼接读取到的内容
		}

		if errors.Is(err, os.ErrClosed) || (errors.Is(err, io.EOF)) {
			break
		}
		if err != nil {
			fmt.Println("读取文件失败:", err)
			return
		}
	}
	fmt.Printf("读取到的内容%s", readData)
}
func bufferIo() {
	filePath := "test_buffer.txt"
	//1.缓冲写
	file, err := os.OpenFile(filePath, os.O_CREATE|os.O_RDWR, 0666)
	if err != nil {
		fmt.Println("打开文件失败:", err)
	}
	defer file.Close()
	//file.Write()
	writer := bufio.NewWriter(file) //创建缓冲写入器
	_, err1 := writer.WriteString("Hello,go语言")
	if err1 != nil {
		fmt.Println("写入失败:", err)
	}
	//fmt.Println("写入字节数:", i)
	err = writer.Flush() //刷新缓冲区,将缓冲区的数据写入文件中
	if err != nil {
		fmt.Println("刷新缓冲区失败:", err)
	}

	//2.缓冲读
	file, err = os.Open(filePath)
	if err != nil {
		fmt.Println("打开文件失败:", err)
		return
	}
	defer file.Close()
	read := bufio.NewReader(file) //创建缓冲读取器
	for {
		line, err := read.ReadString('\n')
		if err != nil {
			if err == io.EOF {
				break
			}
			fmt.Println("读取文件失败:", err)
			return
		}
		fmt.Println("读取到行:%s", line)
	}
}
func sayHello(name string) {
	fmt.Printf("Hello,%s", name)
}
func goroutine() {
	//用go关键字启动goroutin 异步执行sayHello
	go sayHello("go语言")
	time.Sleep(time.Second)
	fmt.Println("主goroutin结束了")
}
func goroutin1() {
	// 启动匿名函数的goroutine
	go func(msg string) {
		for i := 0; i < 3; i++ {
			fmt.Printf("子goroutine：%s - %d\n", msg, i)
			time.Sleep(500 * time.Millisecond)
		}
	}("测试消息") // 匿名函数的参数

	// 主goroutine执行自己的逻辑
	for i := 0; i < 2; i++ {
		fmt.Printf("主goroutine：%d\n", i)
		time.Sleep(500 * time.Millisecond)
	}

	// 等待子goroutine执行完
	time.Sleep(1 * time.Second)
}
func goroutin2() {
	for i := 0; i < 3; i++ {
		go func() {
			fmt.Printf("错误实例:%d\n", i)
		}()
	}
	time.Sleep(time.Second)
}

// 这个问题已经被新版本修复了
func goroutin3() {
	var wg sync.WaitGroup
	for i := 0; i < 3; i++ {
		wg.Add(1)
		go func() {
			defer wg.Done()
			// 子 goroutine 启动后先等待，让主 goroutine 把循环跑完
			time.Sleep(time.Microsecond * 50)
			fmt.Printf("错误示例：%d\n", i) // 现在会稳定输出 3 3 3
		}()
	}
	wg.Wait()
}
func task(id int, wg *sync.WaitGroup) {
	//必须defer done 确保是否无论panic,都会执行
	defer wg.Done()
	fmt.Printf("任务%d开始执行\n", id)
	time.Sleep(time.Second)
	fmt.Printf("任务%d执行完毕\n", id)
}

// waitGroup 相当于java的countDowlaunch
func goroutin4() {
	var wg sync.WaitGroup //WaitGroup 的本质是一个计数器，wg.Add(n) 的核心作用是：给这个计数器增加 n 个 “需要等待完成的任
	taskNum := 5

	// 启动5个goroutine
	for i := 1; i <= taskNum; i++ {
		wg.Add(1)       // 计数器+1 wg.Add(n) 里的 n 是 “待完成任务数” 也就说你这个写几次 defer wg.Done() 这个要写几个
		go task(i, &wg) // 注意：WaitGroup必须传指针，否则是值拷贝，计数器无效
	}
	fmt.Println("所有任务已启动，等待完成...")
	wg.Wait() // 阻塞直到所有Done被调用
	fmt.Println("所有任务执行完毕")
}

// Channel 是 goroutine 之间的 “管道”，既可以传递数据，也可以实现同步。
/**
channel+goroutine 一起有点像java的异步编排
*/
func goroutin5() {
	ch := make(chan string)
	go func() {
		fmt.Println("子goroutine开始执行")
		ch <- "Hello,Go语言" // 阻塞，直到有接收方
		fmt.Println("子goroutine：数据发送完成")
	}()
	fmt.Println("主goroutine：准备接收数据")
	msg := <-ch // 阻塞，直到有发送方
	fmt.Println("主goroutine：收到数据：", msg)
}
func goroutin6() {
	ch := make(chan int, 2)
	ch <- 1
	ch <- 2
	fmt.Println("发送了俩个数据缓冲区已满")
	fmt.Println("开始接收数据")
	fmt.Println("接受数据", <-ch)
	//再发送1个数据,缓冲区又满
	ch <- 3
	//遍历接受所有数据(直到channel关闭)
	defer close(ch) //类似java的关闭流 defer类似java的finally
	for num := range ch {
		fmt.Println("接受数据:", num)
	}

}

/*
*
单向Channel
*/
func producer(ch chan<- int) {
	defer close(ch)
	for i := 0; i < 5; i++ {
		ch <- i
	}
	fmt.Println("生产者结束")
}

// 消费端是没有关闭流权限的
func consumer(ch <-chan int) {
	for num := range ch {
		fmt.Println("消费者：", num)
	}
}
func goroutin7() {
	ch := make(chan int, 2)
	go producer(ch)
	consumer(ch)
}

/*
3. 同步方式 3：sync.Mutex（互斥锁）
用于解决共享资源竞争（多个 goroutine 同时修改同一个变量），核心方法：
mutex.Lock()：加锁，独占资源；
mutex.Unlock()：解锁，释放资源（必须defer，避免 panic 导致锁未释放）。
*/

func increment() {
	defer wg.Done()
	for i := 0; i < 1000; i++ {
		mutex.Lock()
		count++
		mutex.Unlock()
	}
}
func goroutin8() {
	wg.Add(2)
	go increment()
	go increment()

	wg.Wait()
	fmt.Printf("最终计数器值：%d（预期2000）\n", count)
}

func makeOrNotMakeDiff() {
	//不make:仅声明,未初始化底层数组
	var s []int
	fmt.Println("不make的切片:", s)
	fmt.Println("s==nil?:", s == nil) //输出:true
	fmt.Println("len(s):", len(s))    //输出:0
	fmt.Println("cap(s)", cap(s))     //输出:0
	// 错误：直接索引赋值会panic（无底层数组）
	// s[0] = 1 // panic: index out of range [0] with length 0

	// 可以append：首次append会自动分配底层数组
	s = append(s, 1)
	fmt.Println("append后：")
	fmt.Println("s == nil:", s == nil) // 输出：false（已分配底层数组）
	fmt.Println("s:", s)               // 输出：[1]

	// make创建：指定长度3，容量3
	s1 := make([]int, 3)
	fmt.Println("make的切片：")
	fmt.Println("s == nil:", s1 == nil) // 输出：false
	fmt.Println("len(s):", len(s1))     // 输出：3
	fmt.Println("cap(s):", cap(s1))     // 输出：3

}
func reflect() {

}
func main() {
	// 1. 共享资源（所有 goroutine 想修改的变量）

	// 2. 创建 Channel：传递“加 1”的指令（这里用 bool 占位，仅表示“要加 1”）
	ch := make(chan bool, 10000)

	// 3. 专属 goroutine：唯一能修改 count 的角色（串行处理，无竞争）
	go func() {
		for range ch { // 只要 Channel 有指令，就执行加 1
			count++
		}
	}()
	time.Sleep(10 * time.Second)
	fmt.Println("当前的count", count)
	// 4. 启动 10 个 goroutine，每个发 1000 个“加 1”指令（不直接改 count）
	var wg sync.WaitGroup
	wg.Add(10)
	for i := 0; i < 10; i++ {
		go func() {
			defer wg.Done()
			for j := 0; j < 1000; j++ {
				ch <- true // 发指令：要加 1
			}
		}()
	}

	// 5. 等所有指令发完，关闭 Channel
	wg.Wait()
	close(ch)

	// 6. 等专属 goroutine 处理完所有指令（sleep 简化，生产用 doneChan 更严谨）
	time.Sleep(100 * time.Millisecond)

	// 7. 结果必然是 10000（无任何竞争）
	fmt.Printf("最终 count = %d（预期 10000）\n", count)
}
