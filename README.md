
This project includes a library for tracing your application's handled & unhandled exceptions,
in your app’s Room DB, and sending it to your server when possible.


Integration guide for the library -

* First add the library to your project

* Sync project

To use library -

Add “Tracer.createInstance(this)” in the onCreate of your application class. 
this step will initialize the library and will send each unhandled exception automatically to the server.

Use everywhere you want to log your catched exception - 
“Tracer.log(error, "Your description for the problem")”
