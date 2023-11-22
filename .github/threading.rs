use std::thread;
use std::sync::{Arc, Mutex};
use std::time::Duration;

fn main() {
    let counter = Arc::new(Mutex::new(0));

    let mut handles = vec![];

    for _ in 0..1000 {
        let counter = Arc::clone(&counter);
        let handle = thread::spawn(move || {
            let mut num = match counter.lock() {
                Ok(n) => n,
                Err(e) => {
                    eprintln!("Error when locking: {:?}", e);
                    return;
                },
            };
            *num += 1;

            println!("This is job number {} from the spawned thread!", *num);
            thread::sleep(Duration::from_millis(1));
        });
        handles.push(handle);
    }

    for handle in handles {
        if let Err(e) = handle.join() {
            eprintln!("Error when joining: {:?}", e);
        }
    }

    let result = match counter.lock() {
        Ok(n) => *n,
        Err(e) => {
            eprintln!("Error when locking: {:?}", e);
            return;
        },
    };

    println!("Result: {}", result);
}
