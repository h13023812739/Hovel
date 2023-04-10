package org.hyq.hovel.model.maze;

import lombok.Data;
import org.hyq.hovel.enums.BlockEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class Path {

//	@Resource
//	private Maze maze;
	
	BlockEnum[] directions = new BlockEnum[] {BlockEnum.UP, BlockEnum.DOWN, BlockEnum.LEFT, BlockEnum.RIGHT};
	
	public boolean find(Maze maze) throws Exception {
		BlockEnum[][] mazBlockEnum = maze.getMaze();
		Block current = maze.IN;
		if (current.equals(maze.OUT)) {
			return true;
		}
		
		List<Block> list = new ArrayList<>();
		list.add(current.up());
		list.add(current.down());
		list.add(current.left());
		list.add(current.right());
		for (int i = 0; i < list.size(); i++) {
			Block block = list.get(i);
			
			if (block != null && mazBlockEnum[block.row][block.col] == BlockEnum.ROAD) {
				mazBlockEnum[current.row][current.col] = directions[i];
//				maze.draw();
				Thread.sleep(50);
				if (find(maze))
					return true;
			}
		}
		mazBlockEnum[current.row][current.col] = BlockEnum.BACK;
//		maze.draw();
		Thread.sleep(50);
		
		return false;
	}
}