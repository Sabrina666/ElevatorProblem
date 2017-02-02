package boa.elevator;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ElevatorProblemMain {

	public static void main(String args[]){
		ElevatorController eleController = new ElevatorController();
		
		if(args.length!=2){
			System.out.println("Error: Expecting two arguments. Seeing " + args.length);
			return;
		}
		
		List<String> commandList = new ArrayList<String>();
		try (Stream<String> stream = Files.lines(Paths.get(args[0]))) {
	        stream.forEach(commandList::add);
		}catch(Exception e){
			System.out.println("Unable to read file " + args[0]);
		}
		eleController.getRoutes(commandList, args[1]);
	}
}
