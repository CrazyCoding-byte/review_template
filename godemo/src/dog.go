package src

type Dog struct{ Name string }

func (d Dog) Speak() string {
	return "wangwang"
}
