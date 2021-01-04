# Todo List CLI Application

This is a command-line (CLI) program that lets you manage your todos.

## Usage

### 1. Help

```
$ ./todo help
Usage :-
$ ./todo add "todo item"  # Add a new todo
$ ./todo ls               # Show remaining todos
$ ./todo del NUMBER       # Delete a todo
$ ./todo done NUMBER      # Complete a todo
$ ./todo help             # Show usage
$ ./todo report           # Statistics
```

### 2. List all pending todos

```
$ ./todo ls
[2] change light bulb
[1] water the plants
```

### 3. Add a new todo

```
$ ./todo add "the thing i need to do"
Added todo: "the thing i need to do"
```

### 4. Delete a todo item

```
$ ./todo del 3
Deleted todo #3
```

### 5. Mark a todo item as completed

```
$ ./todo done 1
Marked todo #1 as done.
```

### 6. Generate a report

```
$ ./todo report
yyyy-mm-dd Pending : 1 Completed : 4
```

#### This program was created in order to try and get selected in Coronavirus Fellowship Program.
