## StudentManagerGUI.java
This is the main file that is used to run the program
    ## Functions
        ## Add Class
        The Add Class button allows the user to add a new class
        First Dialog Box
            Allows the User to Name the Class
        Second Dialog Box
            Allows the User to add a Name of the Student and Their Address
            Click Add Student to add the student to the roster then the feilds are cleared to allow for another students name and address to be entered
            When all students are added Click done to bring the main window back into focus
        ## Edit Class
        The Edit Class button brings up a dialog box that allows the user to edit the exisiting student information as well as add additional students
        ## Delete Class
        The Delete Class button allows the user to delete an exisitng class. The Class that is currently selected in the class drop down menu is the one that will be deleted.  When clicked a dialog  box asking if the user is sure that they want to delete the selected class will pop up.  If Yes is clicked the class will be deleted, but if the No or the dialog box is closed the class will not be deleted.
        ## Sort by Drop Down
        The Sort by drop down menu has two options, to sort by Name, which sorts the class roster by name alphabetically, or by Roll Number, which sorts the class roster by roll number.
            Note: When students are added during the add class or edit class function they are assigned a roll number by the order that they are added to the roster.
    
    ## Notes
        The Students are stored in a CSV file that is stored in the project directory.  The CSV file is loaded when the program starts and saved when the program closes.  There is additional functionality to load from another CSV file stored on the users machine.  The CSV file can also be saved in another directory.  This function is only for accessibility purposes and can be ignored for general use purposes.  If this is the first time that the program is being launched a placeholder list of students and addresses will be loaded and saved upon the close of the program.  
