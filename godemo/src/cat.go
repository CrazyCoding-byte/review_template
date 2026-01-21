package src

type Cat struct {
	Name string
}

func (c Cat) Speak() string {
	return "miao"
}
