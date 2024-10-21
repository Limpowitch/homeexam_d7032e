///compile with javac -p lib -d bin src/module-info.java src/card/*.java src/counter/*.java src/exam/*.java src/network/*.java src/pile/*.java src/player/*.java src/score/*.java src/view/*.java

package main;

import pointSalad.PointSalad;

public class Main {
	public static void main(String[] args) {
		IGame game = new PointSalad(args); //May be replaced with pointcity
										   //Alternatively, add a new implimentation of pointcity here
	}
}
