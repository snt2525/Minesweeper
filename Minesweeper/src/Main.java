/*
 
 �׽�Ʈ ���̽�
[�Է� MAP]
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
		//����ã�� ���� MAP �Է�
		setGameMap();
	}
	
	public void setGameMap() {
		Queue<Pair> mine = new LinkedList<Pair>();
		
		System.out.println("[����ã�� MAP�� �Է��ϼ���]");
		
		for(int i = 0;i < 10;i++) {
			String tmp = sc.nextLine();
			for(int j = 0;j < 10;j++) {
				map[i][j] = tmp.charAt(j);
				if(map[i][j] == '*') {
					mine.add(new Pair(i , j));
					
					//map�� �ִ� ���� ���� ����.
					clickCnt--;
				}
			}
		}
		System.out.println();
		printGameManual(); 
		
		//��� �簢��(100��)�� ���� ����� result �迭�� �־�д�. 
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
				System.out.println("[����]�� �����Ͽ� ������ ����Ǿ����ϴ�.");
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
							//map�� ���ڰ� 0�� ���, ���������� ���ڸ� �����ر� ���� Queue�� �ش� ��ġ�� �ִ´�.
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
		System.out.println("��ǥ�� �Է����ּ���. :");
		Pair tmp = new Pair(sc.nextInt() - 1, sc.nextInt() - 1);
		
		//�̹� ������ ��ǥ�� �ٽ� �Է� �� ��� ����ó��
		while(click[tmp.y][tmp.x]) {
			System.out.println("������ �� ���� ��ǥ �Դϴ�.");
			System.out.println("��ǥ�� �Է����ּ���. :");
			tmp = new Pair(sc.nextInt(), sc.nextInt());
		}
		
		return tmp;
	}
	
	//STEP���� ����ã�� ���� MAP�� ���¸� ����Ͽ� �ش�.
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
	
	//���ڸ� ����� ��, �� ���� ������ MAP�� ������ ��ġ�� �Բ� ����Ͽ� �ش�.
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
		System.out.println("[���� ����]");
		System.out.println("1. y�� x�� �ּ� ũ��� 1, �ִ� ũ��� 10 �Դϴ�.");
		System.out.println("2. �̹� �����ִ� MAP ĭ�� �ٽ� ������ �� �����ϴ�.");
		System.out.println("3. x�� y�� ���̿� ������ �ΰ� �Է����ּ���. * ����(��ǥ(3,4)�� �Է��� ��, '3 4'�� �Է��Ѵ�.)");	
		System.out.println("");
		System.out.println("*********/// GAME START ///*********");
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Main GAME = new Main();
				
		//���� ����
		GAME.minesweeper();
		
		sc.close();
	}
}
