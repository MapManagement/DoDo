# Plans
## Application
TNo matter if you use the local or a remote database, the application will not change.
The core features of the application will still be same if you decide to store all data
on a remote database. The user can do following things:
- creating ToDos/Tasks which can be set to done and not done  
  (e.g "water plants after work")
- creating Notes which can contain more information (maybe pictures and formatted text as well)  
  (e.g. "summary of the topic for an exam")
- creating Profiles to divide your ToDos and Note (e.g. work and private Profile)  
  (e.g. one "job" Profile and one "private" Profile)
- creating Tags (only Notes) to sort your Notes by categories  
  (e.g. "school" or "shopping")

## Local Database
The local database will be an instance of SQLite3. It should be powerful enough to manage
all stored ToDos and Notes. All data is only on your phone and will not be transferred to any
servers.

## Remote Database
The database runs on a server of your choice. You need to use my server program to transfer
all stored data. It will create all needed tables and will write all objects which were
created by a client to the database.

## Work In Progress
- communication between client and server
- only local or remote or both simultaneously
- initial decision of using a remote database
  - how to sync local and remote database