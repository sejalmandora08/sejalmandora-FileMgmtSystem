package sctpl.javaInternship.filemanager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author SCTPL
 */
public class FileUtility {
    // constant variable declaration
    private static final String zipFolder = "F:\\zip folder\\";

    private static final String extractFolder = "F:\\Extracted Files";

    private static final String encryptedFileLocation = "F:\\Encrypted Files\\";

    private static final String decryptedFileLocation = "F:\\Decrypted File\\";


    ///file operations
    public static void createFile(String inputFile, String content) throws IOException {
        try {
            File file = new File(inputFile);
            // if file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            //get the file in File writer
			FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());


            
            //initialize the buffer writter with the file
			BufferedWriter bufferWriter = new BufferedWriter(fileWriter);

            
            //write the content in the file
			bufferWriter.write(content);

            
            //flush and close the buffer
            bufferWriter.flush();
            bufferWriter.close();

            System.out.println("File " + inputFile + " has been created successfully..!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public static void reNameFile(String inputFile, String newFileName) {
        //get the file path in File object
        File file = new File(inputFile);
        //validate the file
        if (file.isFile()) {
            String fileDirectory = file.getParent();
            File newName = new File(fileDirectory + "\\" + newFileName);
            //perform rename and check it also
            if (file.renameTo(newName)) {
                System.out.println("File has been Renamed.");
            } else {
                System.out.println("Error in Renaming the file.");
            }
        } else {
            System.out.println("Invalid file path");
        }
    }

    public static void deleteFile(String inputFile) {
        //get the file path in File object
        File file = new File(inputFile);
        //validate the file
        if (file.isFile()) {
            try {
                //delete and check if the opertaion has completed or not
				boolean delStatus = file.delete();    
			if(delStatus)
			{
				System.out.println("File Successfully Deleted!");
			}
			else{
				System.out.println("Error in deleting the file");	
			}
			
						
			}catch (Exception e) {
            e.printStackTrace();
        }
		}
    }

    public static void createDirectory(String inputDirectory) {
        //get the file path in File object
        File file = new File(inputDirectory);
        if (file.exists()) {
            System.out.println("The directory is already present");
        } else {
            //use mkdirs() or mkdir() and check its return value
			file.mkdir();
		}
		System.out.println("Successfully created the directory");
            
	}

    public static void reNameDirectory(String inputFile, String newDirName) {
        //get the file path in File object
        File file = new File(inputFile);
        if (file.isDirectory()) {
            File newName = new File(file.getParent() + "\\" + newDirName);
            //use renameTo() and check its return value 
			
			if(file.renameTo(newName)==true)
				System.out.println("Directory has been renamed successfully");
			else
				System.out.println("Directory has not been renamed successfully");
				
			
		}
    }

    public static void deleteDirectory(String inputDirectory) {
        //get the file path in File object
		        File directory = new File(inputDirectory);

        
		
        //check if its a directory  or not
			        if(directory.isDirectory())

        
		
        //check if the directory has childs or not
            if (directory.list().length == 0) {
                directory.delete();
                System.out.println("Directory is deleted : "
                        + directory.getAbsolutePath());
            } else {
        //ask user whether he wants to delete the directory or not
			
		System.out.println("The specified directory is not empty. Confirm Delete?");        
		Scanner s = new Scanner(System.in);
		String userRes= s.nextLine();
				
		if(userRes.equalsIgnoreCase("yes")){
                
		//delete files inside the directory one by one
			deleteFilesInsideDirectory(directory);
       //delete parent directory
			directory.delete();

       if (!directory.exists()) {
       System.out.println("Directory has been deleted.");
       } 
	   else {
       System.out.println("Deletion failed");
       }
       } 
	   else if (userRes.equalsIgnoreCase("no")) {
       System.out.println("Delete directory request cancelled by user.");
       } else {
       deleteDirectory(inputDirectory);
       }
       }
	   else {
       System.out.println("Invalid file directory");
       }
    }

	// code this method in a recursive fashion
    private static void deleteFilesInsideDirectory(File element) {
        if (element.isDirectory()) {
            if (element.listFiles().length == 0) {
    //delete directory
                element.delete();
            } else {
    //delete files one by one
    for (File sub : element.listFiles()) {
				//recurse the function if directory has the file
				deleteFilesInsideDirectory(sub);
			}
	
	
	
    } // end of else-part
    } // end of outer-if
      
	  // delete file
      element.delete();
    }
	
	
	static int count=0;
	static int temp=0;
    public static void listFilesFromDirectory(String inputDirectory) {
        //get the file path in File object
        File directory = new File(inputDirectory);
        //check if its a directory  or not
        if (directory.isDirectory()) {
            //check if the directory has childs or not
			if (directory.list().length == 0)
			{
			System.out.println("The specified directory doesn't conatin any sub-files or sub-folders");
			}
			else{
			//seperate the files and print [Folder] or [File]
				File lstFiles[] = directory.listFiles();
				for (int i = 0; i < lstFiles.length; i++) {
			//show file names here by using file.getName()
			
			if(lstFiles[i].isDirectory())
			{
			temp=count;
			while(temp!=0)
			{
				System.out.println("\t");
				temp--;
			}
			
			System.out.println(lstFiles[i].getName()+"[Dir]");
			count++;
			listFilesFromDirectory(lstFiles[i].getName());
			count--;
			
			}
			else if(lstFiles[i].isFile())
			{
			temp=count;
			while(temp!=0)
			{
				System.out.println("\t");
				temp--;
			}
			System.out.println(lstFiles[i].getName()+"[File]");
			
			}
			}
        }
		}
		else 
		{
            System.out.println("Invalid file directory");
        }
    }

	
    public static void copyFile(String inputPath, String outputPath) throws IOException {

		//InputStream is = null;
        //OutputStream os = null;
        File inputFile = new File(inputPath);
        File outputFile = new File(outputPath + "//" + inputFile.getName());

	    // Check if same fileName or DirectoryName does not exist
		if (inputFile. isFile()) {

	    InputStream is = new FileInputStream(inputFile);
		OutputStream os = new FileOutputStream(outputFile);
		byte[] buffer = new byte[1024];
		int length;
		//write the streams to the OutputStream to copy the data
		while((length=is.read(buffer))>0)
		{
			os.write(buffer, 0, length);
		}
		System.out.println("File copied successfully!!");
		} 
		else {
            System.out.println("Please enter valid file path");
        }
	}

        


// --------------------
//Phase 2 of the project : security operations
	public static void encryptFile(String inputPath) 
	{
		File inputFile = new File(inputPath);
		//check if file is exist or not
			if (inputFile.exists() && inputFile.isFile())
			{
				//create directory if not present
      
				//Encrypted file location
				File encryptedFileLocation = new File(FileUtility.encryptedFileLocation + inputFile.getName());
				try
				{
					//Encrypt file
					EncryptDecrypt.encryptFile(inputFile, encryptedFileLocation);
				}	
				catch (Exception ex) 
				{
					ex.printStackTrace();
				}
			} 
			else 
			{
				System.out.print("\nInvalid file path \n");
			}

		}
	}

		
    public static void decryptFile(String inputPath) 
	{
		File inputFile = new File(inputPath);
        //check if file is exist or not
			if (inputFile.exists() && inputFile.isFile())
			{
				//create directory if not present
        
				//Decrypted file location
				File decryptedFileLocation = new File(FileUtility.decryptedFileLocation + inputFile.getName());
				try 
				{
					//Decrypt file
					EncryptDecrypt.decryptFile(inputFile, decryptedFileLocation);
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
				}
			}
			else 
			{		
				//show error if file is not present
				System.out.print("\nInvalid file path \n");
			}	
	  
		}
	}
	

// --------------

   //Phase 3 of the project : compress - decompress
    public static void compressFile(String inputFile) throws IOException {

        File file = new File(inputFile);
        String fileNameWithExtension = file.getName();
        String fileName = fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf("."));

        //make directory if not exists
					ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(fileName+".zip"));

       //perform zip - logic and code explained in the pdf doc 
					ZipEntry zipEntry = new ZipEntry(fileNameWithExtension);
					zip.putNextEntry(zipEntry);

					FileInputStream fis = new FileInputStream(file);
					final int BUFFER = 2048;
					byte buffer[] = new byte[BUFFER];
					int length;
					while ((length = fis.read(buffer)) > 0) {
		zip.write(buffer, 0, length);
		}
		zip.closeEntry();
		zip.flush();
		fis.close();
		zip.close();
		
        System.out.print("\nFile has been compressed successfully..!\n");
    }
	}

    public static void decompress(String zipFile) {
		
		File file = new File(zFile);
		ZipFile zip = new ZipFile(file);
		BufferedInputStream is=null;
			  boolean flag=false;
			  
			File directory=new File(extractFolder);
			 if(!directory.exists())
			  {
					flag=directory.mkdir();
			  } 
		
		

		//perform un-zip - logic and code explained in the pdf doc 
		Enumeration zipFileEntries= zip.entries();
		while (zipFileEntries.hasMoreElements())
		{
			//decompress here
			ZipEntry entry = (ZipEntry)zipFileEntries.nextElement();
			BufferedInputStream is = new BufferedInputStream(zip.getInputStream(entry));
			//establish buffer for writing into file
			final int buffer=2048;
			//write the current file to 
			byte data[]=new byte[BUFFER];
			FileOutputStream fos=new FileOutputStream(destFile);
			BufferedOutputStream fos=new BufferedOutputStream(fos,BUFFER);
			while((currentByte=is.Read(data,0,buffer))!=-1)
			{
			dest.write(data,0,currentByte);
			}
			
			dest.flush();
			dest.close();
			is.close();
		}
	}
		
		File file = new File(zipFile);
		final int BUFFER=2048;
        String fileNameWithExtension = file.getName();
        String fileName = fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf("."));
		
		BufferedInputStream is=new BufferedInputStream(zip.getInputStream(entry));
		int currentByte;
		byte data[]=new byte[BUFFER];
		File outputStream fos=new FileOutputStream(destFile);
		BufferedOutputStream fos=new BufferedOutputStream(fos,BUFFER);
		while((currentByte=is.Read(data,0,buffer))!=-1)
			dest.write(data,0,currentByte);
		
	
	
// end of FileUtility class
}

