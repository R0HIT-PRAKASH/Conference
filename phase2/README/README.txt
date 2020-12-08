===== GETTING STARTED =====
    Welcome to the program! If you have not set up the MySQL database, follow the Appendix at the bottom of the file. We begin in ProgramMain. Run the main method and let's begin.

    You will be asked if you would like to read from a file or not:
    If you choose to read from a file, you will be reading from the presets we have provided. See below for details.
    If you choose not to, you will be starting from scratch.

    Follow the prompts on screen for either option. See the defaults provided and adding user section below for
    conditions and additional details.

    You are now asked if you would like to sign in as a new or existing user.
    The new user option is for registering a new account and existing user is for logging in as an existing user.
    If you decide to create a new user, see the new user account conditions section.
    If working with the defaults provided and signing in as an existing user, see the defaults section below
    for the usernames and passwords of existing users.

    The following section is about specific users and what they can do:

===== USER MANUAL =====
    === Attendees and VIPs ===
    Once they log in, they will be shown a menu of 5 options.
        Messages (0)- they may:
            - see their inbox
            - see starred, deleted, or archived messages
            - send a message
        Events (1)- they may:
            - view event list, and their scheduled events
            - sign up for, and search for events
            - cancel event reservations
        Requests (2)- they may view their requests, or make requests
        User Options (3)- they may:
            - view their corporation and edit their corporation information
            - view their bio, and edit their bio
    Quit (4)
    At any menu, there is the option of going back to the main screen.

    === Speakers ===
    Once they log in, they will be shown a menu of 5 options.
        Events (1)- they may:
            - view their scheduled events
        Requests (2)- they may view their requests, or make requests
        User Options (3)- they may:
            - view their corporation and edit their corporation information
            - view their bio, and edit their bio
    Quit (4)

    === Organizers ===
    Once they log in, they will be shown a menu of 5 options.
        Messages (0)- they may:
            - see their inbox
            - see starred, deleted, or archived messages
            - send a message
            - message attendees at one of their events.
        Events (1)- they may:
            - view event list, and their scheduled events
            - sign up for, and search for events
            - cancel event reservations and modify their capacity
            - reschedule events
            - add rooms
            - get conference stats
            - search for events
        User Options (2)- they may:
            - view information like rooms, speakers, organizers, attendees, vips
            - view their corporation and edit their corporation information
            - view their bio, and edit their bio
        Requests (3)- they may view their requests, or make requests
    Quit (4)
    At any menu, there is the option of going back to the main screen.



===== NAVIGATING =====
    Once you follow all prompts and are logged into your account, you will be provided a menu of options. Enter the
    corresponding number to perform that task.
    After completing a task, you can enter ask to be shown the menu again, or end the program.

    The corresponding numbers for these are provided for your reference:
         Organizers: 14 to be shown the menu of options, 20 to end the program
         Attendees: 7 to be shown the menu of options, 8 to end the program
         Speakers: 5 to be shown the menu of options, 6 to end the program

===== DETAILS AND WARNINGS ABOUT SAVING =====
    Once you are done using the program for the time being, and want to save your files, you MUST end the program using
    the reference above, or else your data will not be saved.

    If you choose to completely disregard the default files we have provided and do not need them, move them to a
    different location or delete them so that they do not interfere with the new files that you will save. If you are
    adding onto the defaults we have provided, disregard the previous
    sentence.

===== DEFAULTS PROVIDED =====
    ==Organizers==

    ==Attendees==

    ==Speakers==

    ==VIPs==

    ==Requests==

    ==Messages==

    ==Rooms==

    ==Events==

        ==Parties==

        ==Panels==

        ==Talks==

===== NEW USER ACCOUNT CONDITIONS =====
    If you choose to create a new account, the following conditions MUST hold:
        - username and password must be at least 3 characters,
        - name at least 2 characters, and
        - address at least 6 characters
    The email must also follow standard email conventions.

    You will also be prompted about which company you are associated with.
        - You can either enter 'none' for your company, OR
        - Enter the company name; in this case:
            - you must enter some input that is at least one character


===== APPENDIX: SETTING UP THE MYSQL DATABASE =====
1. Download and install the MySQL Community Server here: https://dev.mysql.com/downloads/mysql/
2. Under your 'System Preferences' (OS Dependent) make sure the server is running.
3. Run the mysql binary in the command line (on mac this is at /usr/local/mysql/bin/ with the command `mysql -u root -p`) and enter the password created during installation. This creates and account with username 'root' and your particular password.
4. In MySQL run 'CREATE DATABASE conference;' and exit.
5. In CreateConferenceTables.java and Connector.java, put in your password. The default is csc@207uoft. If you ran the correct MySQL command, the username remains root.
6. Open up Project settings and make a new Java library with the file JDBC/mysql-connector-java-8.0.22.jar
7. Run CreateConferenceTables.main(). This syncs the MySQL database with the project.
8. Run ProgramMain.main() and everything should work.

