package testJava;

class Student {
	
	public int rollno;
	public String name;
	
	public Student(int rollno ,String name) {
		this.rollno=rollno;
		this.name=name;
	}
	
}	
	class abc {
		public static void main(String args[]) {
			
			Student arr[];
			arr = new Student[5];
			arr[0]=new Student(1,"Aman");
			arr[1]=new Student(2,"kamal");
			arr[2]=new Student(3,"Geeta");
			arr[3]=new Student(4,"Pawan");
			arr[4]=new Student(5,"Chaman");
			
			for (int i=0;i<arr.length;i++) {
				System.out.println("Element at i " + " :" + arr[i].rollno +" : " + arr[i].name);
			}
		}	
	}



