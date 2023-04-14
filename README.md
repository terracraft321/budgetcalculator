

# Budget Calculator

# Development guide
Original project topic: The program can add up different travel costs and other expenses related to the trip.
The program and object class include the following:

* Budget field, i.e., how much budget the user has.
* Selection box so that the user can select how many costs to enter.
* Total cost field where all costs are added up.
* Remaining budget field, i.e., the calculation is budget - total cost.
* Save and open buttons for reading and saving files.
* The program contains Hbox and Vbox, scenes.
* Setters and getters.
* Methods for adding and summing, and main method to print the final results.
* Classes for reading from and writing to a file.
* The class has the following fields, and the following types of methods have been used to update expenses, create fields, read from a file, open the saved file, and finally update the remaining budget.

# Program structure:

![image](https://user-images.githubusercontent.com/6617067/232155143-1d05025a-6ffc-4860-a59a-ffe545274fda.png)

Instructions for using and installing the JavaFX program:

Extract the zip file of the return to the appropriate directory. Start IntelliJ and open the project.
Compile and run the program using IntelliJ. You can transfer completed expenses to the program using the "Open" button from the file created by the program. The program works without a file by selecting the amount of expenses and filling in the required fields. Finally, you can create a new file using the "Save" button that you can share with a friend or family member, or use to edit the same information again later.

To use the modified object class, add the desired expenses to the program and select the name of the file where all the information will be saved if it is to be reused in the future. The output can be read again in another program. The object class performs calculations in its own methods.

If there is anything that is unclear about the use or development of the program, it may be helpful to consult the Javadoc documentation, object class doc, and javafxdoc folders, which contain detailed descriptions of the program's functions and uses.

# Compiling the program

The project includes a file called BudjettiLaskuri.java, which can be compiled for execution with the command if the JDK (Java Development Kit) is installed:

javac BudjettiLaskuri.java

This creates the BudjettiLaskuri.class file. Then you can run this file using the java command as follows:

java BudjettiLaskuri

Use the same procedure for compiling and running the Main.java file.
 
