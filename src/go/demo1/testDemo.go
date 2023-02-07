package demo1

import (
	"fmt"
	"strconv"
	"time"
)

func Test() {
	start := time.Now().Unix()
	str := ""
	for i := 0; i < 100000; i++ {
		str += "hello" + strconv.Itoa(i)
	}
	end := time.Now().Unix()
	fmt.Println("开始到结束花费%", end-start)
}
