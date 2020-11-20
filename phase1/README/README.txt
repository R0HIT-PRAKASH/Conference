===== GETTING STARTED =====
    Welcome to the program! We begin in ProgramMain. Run the main method and let's begin.

    You will be asked if you would like to read from a file or not:
    If you choose to read from a file, you will be reading from the users.ser, messages.ser, events.ser and rooms.ser
    files that we have provided, and you will begin the program with defaults;
    If you choose not to read from a file, you will not be starting with any defaults.

    To read from the files and start with defaults, you will find an input folder in our submission file. You need to
    move the four files in the input folder to the outermost group_0564 folder in order for the program to read them. If
    you do not do this, the program will not be able to read the input files and you will not start the program with
    any defaults.

    Follow the prompts on screen for either option. See the defaults provided and adding user section below for
    conditions and additional details.

    You are now asked if you would like to sign in as a new or existing user.
    The new user option is for registering a new account and existing user is for logging in as an existing user.
    If you decide to create a new user, see the new user account conditions section.
    If working with the defaults provided and signing in as an existing user, see the defaults section below
    for the usernames and passwords of existing users.

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
    (1) username: esherman, password: hello11 [name: Ellis Sherman, address: 100 Demo Circle, email: ellis@email.com]
    (2) username: ablythe, password: this22 [name: Aizah Blythe, address: 22 CSC207 Road, email: aizah@email.com]

    ==Attendees==
    (1) username: mkent, password: is33 [name: Melody Kent, address: 16 Phase One Blvd, email: melody@email.com]
    (2) username: jdalton, password: a44 [name: Jillian Dalton, address: 48 Read Me Dr, email: jillian@email.com]
    (3) username: jframe, password: demo55 [name: Jamil Frame, address: 29 Attendee Street, email: jamil@email.com]

    ==Speakers==
    (1) username: kmalone, password: its88 [name: Kerry Malone, address: 57 Program Main, email: kerry@email.com]
    (2) username: fwiley, password: user99 [name: Ferne Wiley, address: 92 Ser File, email: ferne@email.com]
    (3) username: cwhitmore, password: settings [name: Cat Whitmore, address: 58 Terminal Road, email: cat@email.com]

    ==Messages==
    jframe --> mkent 'Are you going to event1'
    mkent --> jframe 'Yes!'

    ==Rooms==
    Rooms: [‘101’, ‘102’, ‘103’, ‘104’]

    ==Events==
    {name: event1, speakerName: kmalone, time: 2020-11-28-09:00, roomNumber: 101}
    {name: event2, speakerName: fwiley, time: 2020-11-28-10:30, roomNumber: 102}
    {name: event3, speakerName: kmalone, time: 2020-11-28-10:00, roomNumber: 103}
    {name: event4, speakerName: fwiley, time: 2020-11-28-13:00, roomNumber: 102}
    {name: event5, speakerName: fwiley, time: 2020-11-28-14:00, roomNumber: 104}

    mkent and jdalton are both signed up for event 1.

===== NEW USER ACCOUNT CONDITIONS =====
    If you choose to create a new account, the following conditions MUST hold:
        - username and password must be at least 3 characters,
        - name at least 2 characters, and
        - address at least 6 characters
    The email must also follow standard email conventions.


===== QUESTIONS FOR TA =====
(1) Are our controllers handling too much? If so, how should we go about fixing this?
(2) In what ways can we improve our use of Clean Architecture (please specify which classes if possible)?