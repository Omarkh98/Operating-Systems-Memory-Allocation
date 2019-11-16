package os.ii.project;
import java.util.Scanner;

public class OSIIProject {

    static int MemSize;
    static int i;
    static int Process;
    static Scanner in = new Scanner(System.in);
    static boolean isEmpty;
    static int[][] Mem;
    static int Choice;
    static boolean p = false;
    static int[] PartitionsNo;

    public static void Init() {
        String Partition = new String(); // Partition The Memory into different sizes.
        Scanner Input2 = new Scanner(System.in);

        System.out.println("Enter the size of your Memory: ");
        MemSize = in.nextInt();   // Static Memory Size.

        System.out.println("Partition the memory as you like: ");
        Partition = Input2.nextLine();

        String[] PartitionsSTR = Partition.split(" ");    // Get Each partition in the string and jump the Space.

        PartitionsNo = new int[PartitionsSTR.length];   // Array that takes the Length of our Partitions.
        int PartitionsSUM = 0;

        for (i = 0; i < PartitionsSTR.length; i++) {
            PartitionsNo[i] = Integer.parseInt(PartitionsSTR[i]);   
            PartitionsSUM += PartitionsNo[i];
        }
        if (PartitionsSUM != MemSize) // If our TOTAL Partitions are Not EQUAL to our predefined memory size; That's a fatal Error!
        {
            System.out.println("Your numbers are either less than the memory size or have exceeded the memory size");
            Init();    // Restart The WHOLE operation again.
        }
        // Else Succeded.
        Mem = new int[PartitionsNo.length][2];

        for (i = 0; i < Mem.length; i++) {
            Mem[i][1] = PartitionsNo[i]; // initializing Mem[i][1] to every partiton number
        }
        MemView();
        System.out.println("Enter 1 --> for First-Fit , 2 --> for Best-Fit , 3 --> for Worst-Fit");
        Choice = in.nextInt();    // Get the Algorithm Choice from the User.

        if (Choice == 1) // 1 --> First-Fit.
        {
            FirstFit();
        } else if (Choice == 2) // 2 --> Best-Fit.
        {
            BestFit();
        } else if (Choice == 3) {
            WorstFit();  // 3 / Else --> Worst-Fit.
        }
    }

    public static void ProcessInput() {
        System.out.println("Please Enter the Process Size : ");
        Process = in.nextInt();
    }

    public static void MemView() {
        System.out.println("Partition # (Size) : Process  | Hole ");

        for (i = 0; i < Mem.length; i++) {
            System.out.println("Partition " + (i + 1) + "(" + PartitionsNo[i] + " KB)" + ": " + Mem[i][0] + " | " + Mem[i][1]);
        }

    }

    public static void remove() {

        int ProcessSum = 0;
        for (i = 0; i < Mem.length; i++) {
            ProcessSum += Mem[i][0];
        }

        if (ProcessSum == 0) {
            System.out.println("Memory is already empty");
        } else {
            MemView();
            System.out.println("Enter the number of partition you want to remove the process from: ");
            int partNo = in.nextInt() - 1; // taking input from user and decrementing it by 1 to make it suitable for array size

            if (Mem[partNo][0] == 0) {
                System.out.println("Partition No." + (partNo + 1) + " is already empty");
            } else {
                Mem[partNo][1] += Mem[partNo][0]; // increasing the size of the Hole (removed process+ Current size of HOle)
                Mem[partNo][0] = 0; // putting process equal to zero (removing process from partitionNo user chose
                MemView();
            }
        }
        MyOptions();
    }

    public static void MyOptions() {

        System.out.println("Would you like to add (A) or remove (R) a process or Exit (E) ? (A/R/E) ");
        char YN = in.next().charAt(0);
        if (YN == 'a' || YN == 'A') {
            if (Choice == 1) // 1 --> First-Fit.
            {
                FirstFit();
            } else if (Choice == 2) // 2 --> Best-Fit.
            {
                BestFit();
            } else if (Choice == 3) {
                WorstFit();  // 3 / Else --> Worst-Fit.
            }
        } else if (YN == 'R' || YN == 'r') {
            remove();
        } else if (YN == 'e' || YN == 'E') {
            System.exit(0);
        }
    }

    public static void FirstFit() // Allocate the First Big-Enough Hole.
    {
        ProcessInput();  // User Input Function.
        isEmpty = false;
        for (i = 0; i < Mem.length; i++) {
            if (Process <= Mem[i][1] && Mem[i][0] == 0) {
                Mem[i][0] = Process; // putting the process in mem[i][0]
                Mem[i][1] -= Process; // Remove / Subtract the Process size from the Partition at hand.               
                MemView();
                isEmpty = true;
            }
        }

        if (!isEmpty) {
            System.out.println("Memory is Full or you entered a large partition");
        }
        MyOptions();
    }

    public static void BestFit() //  Allocate the Smallest Hole, that Best-Fits.
    {
        ProcessInput();
        int Minimum = 99999;
        int Position = 0;

        isEmpty = false;
        for (i = 0; i < Mem.length; i++) 
        {
            if (Process <= Mem[i][1] && Mem[i][0] == 0) // If the Process Size is LESS than the Hole and the Process Place is EMPTY.
            {
                if (Minimum > Mem[i][1]) 
                {
                    Position = i;
                    Minimum = Math.min(Mem[i][1], Minimum); // Process Will Choose the Minimum value from the Hole that best fits.
                }
               isEmpty = true;
            }
        }

        if (isEmpty) {
            Mem[Position][0] = Process; // putting the process in mem[i][0]
            Mem[Position][1] -= Process; // Remove / Subtract the Process size from the Partition at hand. 
        }
        MemView();
        if (!isEmpty) {
            System.out.println("Memory is Full or you entered a large partition");
        }
        MyOptions();
    }

    public static void WorstFit() // Allocate the Largest Hole.
    {
        ProcessInput();
        int max = 0, pos = 0;

        isEmpty = false;
        for (i = 0; i < Mem.length; i++) 
        {
            if (Process <= Mem[i][1] && Mem[i][0] == 0) 
            {
                if (max < Mem[i][1]) 
                {
                    pos = i;
                    max = Math.max(Mem[i][1], max);
                }
                isEmpty = true;
            }
        }

        if (isEmpty) 
        {
            Mem[pos][0] = Process; // putting the process in mem[i][0]
            Mem[pos][1] -= Process;
        }
        MemView();
        if (!isEmpty) {
            System.out.println("Memory is Full or you entered a large partition");
        }
        MyOptions();
    }

    public static void main(String[] args) {
        Init();
    }
}
