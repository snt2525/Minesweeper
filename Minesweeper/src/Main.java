/*
 
 테스트 케이스
[입력 MAP]
..*....*..
.*..*.*...
..*....*..
..........
...*......
.......*..
.......*..
..*.*..*..
..***.....
..*.......
	
 */

import java.util.*;

class Pair{
	int y;
	int x;
	Pair(int y, int x){
		this.y = y;
		this.x = x;
	}
}

public class Main {
	static Scanner sc;
	int[][] dir = {{0, 1},{1, 0},{0, -1},{-1, 0},
				  {-1, -1},{1, 1},{1, -1},{-1, 1}};
	char[][] map;
	boolean[][] click;
	int[][] result;
	int clickCnt;
	Main(){
		this.sc = new Scanner(System.in);
		this.map = new char[10][10];
		this.result = new int[10][10];
		this.click = new boolean[10][10];
		this.clickCnt = 100;
		//지뢰찾기 게임 MAP 입력
		setGameMap();
	}
	
	public void setGameMap() {
		Queue<Pair> mine = new LinkedList<Pair>();
		
		System.out.println("[지뢰찾기 MAP을 입력하세요]");
		
		for(int i = 0;i < 10;i++) {
			String tmp = sc.nextLine();
			for(int j = 0;j < 10;j++) {
				map[i][j] = tmp.charAt(j);
				if(map[i][j] == '*') {
					mine.add(new Pair(i , j));
					
					//map에 있는 지뢰 수를 뺀다.
					clickCnt--;
				}
			}
		}
		System.out.println();
		printGameManual(); 
		
		//모든 사각형(100개)에 대한 계산을 result 배열에 넣어둔다. 
		int qSize = mine.size();
		for(int i = 0;i < qSize;i++) {
			Pair tmp = mine.poll();
			result[tmp.y][tmp.x] = -1;
			for(int k = 0;k < 8;k++) {
				int tmpY = tmp.y + dir[k][0];
				int tmpX = tmp.x + dir[k][1];
				if(tmpY < 0||tmpX < 0||tmpY >= 10||tmpX >= 10) continue;
				if(map[tmpY][tmpX] != '*') {
					result[tmpY][tmpX]++;
				}				
			}
		}	
		
	}
	
	public void minesweeper() {		
		while(clickCnt > 0) {
			Queue<Pair> btn = new LinkedList<Pair>();
			Pair tmpYX = putYX();
			
			//Game Over
			if(map[tmpYX.y][tmpYX.x] == '*') {
				printBoom();
				System.out.println("[지뢰]가 폭발하여 게임이 종료되었습니다.");
				return;
			}			
			
			btn.add(tmpYX);
			click[tmpYX.y][tmpYX.x] = true;
			clickCnt--;
			
			if(result[tmpYX.y][tmpYX.x] == 0) {
				while(!btn.isEmpty()) {
					Pair tmp = btn.remove();
					for(int i = 0;i < 8;i++) {
						int tmpY = tmp.y + dir[i][0];
						int tmpX = tmp.x + dir[i][1];
						if(tmpY < 0||tmpX < 0||tmpY >= 10|| tmpX >= 10) continue;
						if(!click[tmpY][tmpX] && map[tmpY][tmpX] != '*') {
							click[tmpY][tmpX] = true;
							clickCnt--;
							//map의 숫자가 0일 경우, 연쇄적으로 숫자를 보여준기 위해 Queue에 해당 위치를 넣는다.
							if(result[tmpY][tmpX] == 0) {
								btn.add(new Pair(tmpY, tmpX));
							}
						}
					}
				}
			}
			
			printMap();
		}
	}
	
	public Pair putYX() {	
		System.out.println("좌표를 입력해주세요. :");
		Pair tmp = new Pair(sc.nextInt() - 1, sc.nextInt() - 1);
		
		//이미 선택한 좌표를 다시 입력 할 경우 예외처리
		while(click[tmp.y][tmp.x]) {
			System.out.println("선택할 수 없는 좌표 입니다.");
			System.out.println("좌표를 입력해주세요. :");
			tmp = new Pair(sc.nextInt(), sc.nextInt());
		}
		
		return tmp;
	}
	
	//STEP마다 지뢰찾기 게임 MAP의 상태를 출력하여 준다.
	public void printMap() {
		System.out.println("       [MAP]");
		for(int i = 0;i < 10;i++) {
			for(int j = 0;j < 10;j++) {
				System.out.print((click[i][j] ? result[i][j] : ".") + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	//지뢰를 밟았을 때, 이 전에 선택한 MAP과 지뢰의 위치를 함께 출력하여 준다.
	public void printBoom() {
		System.out.println("       [MAP]");
		for(int i = 0;i < 10;i++) {
			for(int j = 0;j < 10;j++) {
				System.out.print((click[i][j] ? result[i][j] : (map[i][j] == '*' ? "*" : ".")) + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void printGameManual() {
		System.out.println("[게임 설명서]");
		System.out.println("1. y와 x의 최소 크기는 1, 최대 크기는 10 입니다.");
		System.out.println("2. 이미 터져있는 MAP 칸은 다시 선택할 수 없습니다.");
		System.out.println("3. x와 y의 사이에 공백을 두고 입력해주세요. * 예제(좌표(3,4)를 입력할 때, '3 4'로 입력한다.)");	
		System.out.println("");
		System.out.println("*********/// GAME START ///*********");
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Main GAME = new Main();
				
		//게임 시작
		GAME.minesweeper();
		
		sc.close();
	}
}
