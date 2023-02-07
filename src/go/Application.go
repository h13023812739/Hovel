package main

import (
	"fmt"
	//"go/demo1"
	"time"
)

func main() {
	start := time.Now().Unix()
	end := time.Now().Unix()
	fmt.Println("开始到结束花费%", end-start)
	//demo1.Test()
}

//func main() {
//	start := time.Now().Unix()
//	str := ""
//	for i := 0; i < 100000; i++{
//		str += "hello" + strconv.Itoa(i)
//	}
//	end := time.Now().Unix()
//	fmt.Println("开始到结束花费%",end-start)
//}
