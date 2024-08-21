import java.util.*;
import java.io.*;

class PackerUnpacker {

    public static void main(String[] args) throws Exception {
        Scanner sobj = new Scanner(System.in);

        System.out.println("-----------------------------------------------------");
        System.out.println("------------------ Packer Unpacker CUI Module -------");
        System.out.println("-----------------------------------------------------");

        while (true) {
            System.out.println("Choose an activity:");
            System.out.println("1. Packing");
            System.out.println("2. Unpacking");
            System.out.println("3. Help");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = sobj.nextInt();
            sobj.nextLine();  
            //String input = sobj.nextLine();  // Read the entire line of input
            //int choice;
            //choice = Integer.parseInt(input);  // Convert the string to an integer

            switch (choice) {
                case 1:
                    performPacking(sobj);
                    break;
                case 2:
                    performUnpacking(sobj);
                    break;
                case 3:
                    displayHelp();
                    break;
                case 4:
                    System.out.println("Thank you for using Marvellous Packer Unpacker tool");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void performPacking(Scanner sobj) throws Exception {
        System.out.println("----------------- Packing Activity ------------------");
        System.out.println();

        System.out.print("Enter the name of the directory that you want to open for packing: ");
        String folderName = sobj.nextLine();

        File folder = new File(folderName);

        System.out.print("Enter the name of the packed file that you want to create: ");
        String packedFileName = sobj.nextLine();

        File packedFile = new File(packedFileName);

        boolean bret = packedFile.createNewFile();
        if (!bret) {
            System.out.println("Unable to create packed file");
            return;
        }

        FileOutputStream foobj = new FileOutputStream(packedFile);

        if (folder.exists()) {
            int iCount = 0;

            File Arr[] = folder.listFiles();

            String header = null;
            int iRet = 0;
            byte buffer[] = new byte[1024];
            FileInputStream fiobj = null;

            for (int i = 0; i < Arr.length; i++) {
                header = Arr[i].getName();

                if (header.endsWith(".txt")) {
                    System.out.println("File packed with name: " + header);

                    header = header + " " + Arr[i].length();

                    for (int j = header.length(); j < 100; j++) {
                        header = header + " ";
                    }

                    foobj.write(header.getBytes(), 0, 100);

                    fiobj = new FileInputStream(Arr[i]);

                    while ((iRet = fiobj.read(buffer)) != -1) {
                        foobj.write(buffer, 0, iRet);
                    }

                    fiobj.close();
                    iCount++;
                }
            }

            System.out.println("-----------------------------------------------------");
            System.out.println("Packing activity completed..");
            System.out.println("Number of files scanned: " + Arr.length);
            System.out.println("Number of files packed: " + iCount);
            System.out.println("-----------------------------------------------------");

            System.out.println("Thank you for using Marvellous Packer Unpacker tool");
            foobj.close();
        } else {
            System.out.println("There is no such directory");
        }
    }

    public static void performUnpacking(Scanner sobj) throws Exception {
        System.out.println("---------------- Unpacking Activity -----------------");
        System.out.println();

        System.out.print("Enter the name of the packed file that you want to open: ");
        String packedFileName = sobj.nextLine();

        File packedFile = new File(packedFileName);

        if (!packedFile.exists()) {
            System.out.println("Unable to proceed as packed file is missing...");
            return;
        }

        FileInputStream fiobj = new FileInputStream(packedFile);

        byte[] header = new byte[100];
        int iRet = 0;
        String headerX = null;
        File obj = null;
        int fileSize = 0;
        FileOutputStream foobj = null;
        int iCount = 0;

        while ((iRet = fiobj.read(header, 0, 100)) > 0) {
            headerX = new String(header);
            headerX = headerX.trim();

            String tokens[] = headerX.split(" ");

            obj = new File(tokens[0]);
            System.out.println("File dropped with name: " + tokens[0]);

            obj.createNewFile();

            fileSize = Integer.parseInt(tokens[1]);
            byte buffer[] = new byte[fileSize];

            fiobj.read(buffer, 0, fileSize);

            foobj = new FileOutputStream(obj);
            foobj.write(buffer, 0, fileSize);

            foobj.close();
            iCount++;
        }

        System.out.println("-----------------------------------------------------");
        System.out.println("Unpacking activity completed..");
        System.out.println("Number of files unpacked: " + iCount);
        System.out.println("-----------------------------------------------------");

        System.out.println("Thank you for using Packer Unpacker tool");

        fiobj.close();
    }

    public static void displayHelp() {
        System.out.println("---------------- Help ----------------");
        System.out.println("This program allows you to pack and unpack text files.");
        System.out.println("1. Packing: Choose this option to pack all .txt files in a directory into a single packed file.");
        System.out.println("   - It will result to enter the directory name to pack.");
        System.out.println("   - It will also result to enter the name of the packed file to create.");
        System.out.println("2. Unpacking: Choose this option to unpack a packed file.");
        System.out.println("   - It will result to enter the name of the packed file to unpack.");
        System.out.println("3. Help: Displays this help message.");
        System.out.println("4. Exit: Exits the program.");
        System.out.println("--------------------------------------");
    }
}
