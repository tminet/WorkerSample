## WorkerSample

<b>WorkerSample</b> is an application that uses `Work Manager` (with `Coroutines`) to perform tasks,
built with `Kotlin`and `Jetpack Compose`.

### Summary

<b>WorkerSample</b> runs a task with `Work Manager` every 15 minutes, saving a record in `SQLite`
table. This task can only be performed when the device is connected to the internet. This
requirement is for example only, no actual network will be performed.

On the application screen it is possible to monitor the following information:

- Device without internet connection.
- When Work is enqueued or running.
- The list of all registered logs.

### Libraries

- Coroutines: Perform asynchronous tasks.
- Room: An abstraction layer over SQLite.
- Work Manager: Persistent tasks, remains scheduled through app restarts and system reboots.
- Hilt: A dependency injection library.

---
