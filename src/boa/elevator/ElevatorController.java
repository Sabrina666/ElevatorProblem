package boa.elevator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ElevatorController {
	private final int TOTAL_FLOOR = 12;
	public void getRoutes(List<String> commands, String mode) {
		if(commands == null || commands.size()<1)
			return;
		for(String command:commands){
			String[] splitCommand = command.split(":");
			switch(mode){
			case "A":
				findModeASequence(splitCommand[0], splitCommand[1].split(","));
				break;
			case "B":
				findModeBSequence(splitCommand[0], splitCommand[1].split(","));
				break;
			default:
				System.out.println("Invalid calcualtion mode " + mode);
				return;
			}
			
		}
	}
	
	private void findModeASequence(String initFloor, String[] commandList){
		if(commandList == null || commandList.length <1){
			return;
		}
		
		List<Integer> travelList = new ArrayList<Integer>();
		int totalDist = 0;
		travelList.add(Integer.parseInt(initFloor));
				
		for(String userCommand:commandList){
			String[] splitCommand = userCommand.split("-");
			if(splitCommand.length!=2){
				System.out.println("Command formate need to be <start floor>-<end floor>, not " + userCommand);
				return;
			}
			int fromFloor = -1;
			int toFloor = -1;
			try{
				fromFloor = Integer.parseInt(splitCommand[0]);
				toFloor = Integer.parseInt(splitCommand[1]);
			}catch(NumberFormatException e){
				System.out.println("Command formate need to be <start floor>-<end floor>, not " + userCommand);
				return;
			}
			
			if(((fromFloor>TOTAL_FLOOR)||(fromFloor<1))||((toFloor>TOTAL_FLOOR)||(toFloor<1))){
				System.out.println("Floor number must be 1 to " + TOTAL_FLOOR);
				return;
			}
			
			if(fromFloor == toFloor){
				continue;
			}
			
			if(travelList.get(travelList.size()-1).intValue() != fromFloor){
				totalDist += Math.abs(fromFloor - travelList.get(travelList.size()-1).intValue());
				travelList.add(fromFloor);
			}
			travelList.add(toFloor);
			totalDist += Math.abs(fromFloor - toFloor);
		}
		
		for(int i = 0; i < travelList.size(); i++){
			System.out.print(travelList.get(i)+" ");
		}
		System.out.print("("+totalDist+")"+"\r\n");				
	}
	
	private void findModeBSequence(String initFloor, String[] commandList){
		System.out.print(initFloor + " ");
		
		if(commandList == null || commandList.length <1){
			return;
		}
		
		Set<Integer> sameDirectComdSet = new HashSet<Integer>();
		TravelDirection setDirection = TravelDirection.NoDirection;
		int totalDist = 0;
		int prevFloor = Integer.parseInt(initFloor);
		
		for(String userCommand:commandList){
			String[] splitCommand = userCommand.split("-");
			if(splitCommand.length!=2){
				System.out.println("Command formate need to be <start floor>-<end floor>, not " + userCommand);
				return;
			}
			int fromFloor = -1;
			int toFloor = -1;
			try{
				fromFloor = Integer.parseInt(splitCommand[0]);
				toFloor = Integer.parseInt(splitCommand[1]);
			}catch(NumberFormatException e){
				System.out.println("Command formate need to be <start floor>-<end floor>, not " + userCommand);
				return;
			}
			TravelDirection comDirection = (toFloor-fromFloor)==0?TravelDirection.NoDirection:( (toFloor-fromFloor)>0?TravelDirection.Up:TravelDirection.Down );
			
			if(((fromFloor>TOTAL_FLOOR)||(fromFloor<1))||((toFloor>TOTAL_FLOOR)||(toFloor<1))){
				System.out.println("Floor number must be 1 to " + TOTAL_FLOOR);
				return;
			}
			
			if(comDirection == TravelDirection.NoDirection){
				continue;
			}
			
			if(setDirection == TravelDirection.NoDirection){
				setDirection = comDirection;
			}
			
			if(comDirection == setDirection){
				sameDirectComdSet.addAll(Arrays.asList(fromFloor, toFloor));
			}else{
				List<Integer> sameDirectList = getAndPrintTravelSequence(sameDirectComdSet, setDirection, prevFloor);
				int dist = getTravelDistance(sameDirectList,prevFloor);
				totalDist += dist;
				
				prevFloor = ((Integer) sameDirectList.get(sameDirectList.size()-1)).intValue();
				sameDirectComdSet.clear();
				setDirection = comDirection;
				sameDirectComdSet.addAll(Arrays.asList(fromFloor, toFloor));	
			}
		}
		
		if(sameDirectComdSet.size()>0){
			List<Integer> sameDirectList = getAndPrintTravelSequence(sameDirectComdSet, setDirection, prevFloor);
			int dist = getTravelDistance(sameDirectList,prevFloor);
			totalDist += dist;
		}
		
		System.out.print("("+totalDist+")"+"\r\n");				
	}
	
	private List<Integer> getAndPrintTravelSequence(Set<Integer> sameDirectComdSet, TravelDirection setDirection, int prevFloor){
		List<Integer> sameDirectList = new ArrayList<Integer>();
		sameDirectList.addAll(sameDirectComdSet);
		Collections.sort(sameDirectList);
		if(setDirection == TravelDirection.Down)
			Collections.reverse(sameDirectList);
		
		if(prevFloor == sameDirectList.get(0).intValue())
			sameDirectList.remove(0);
		
		for(int i = 0; i < sameDirectList.size(); i++){
			System.out.print(sameDirectList.get(i)+" ");
		}
		return sameDirectList;
	}
	
	private int getTravelDistance(List<Integer> sameDirectList, int prevFloor){
		int dist = Math.abs(sameDirectList.get(0).intValue() - prevFloor) + 
				Math.abs(sameDirectList.get(sameDirectList.size()-1).intValue() - sameDirectList.get(0).intValue());
		return dist;
	}
}
