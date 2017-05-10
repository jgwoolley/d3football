package datastructures;

import runner.*;
import datastructures.*;
import functions.*;
import outputs.*;

public class qArray {
	private qPlay[] plays;
	
	public qArray(qPlay[] plays){
		this.plays = plays;
	}
	
	public int size(){
		return plays.length;
	}
	
	public qPlay getPlay(int index){
		return plays[index];
	}
}
