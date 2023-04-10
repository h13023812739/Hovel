package org.hyq.hovel.controller;

import org.hyq.hovel.model.maze.DemoTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drawMaze")
public class HeartMazeController {

    @GetMapping("/show")
    public void drawMaze(){
        DemoTest demo = new DemoTest();
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
        demo.setVisible(true);

    }
}
