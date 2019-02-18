/*
	Cal State Los Angeles
	CS 4220
	Monday Feburary 18th 2019
	Adan Constanzo
	Homework 3
*/

/*
1. Using async/await syntax write a set of functions that adds numbers and awaits for the total.
Requirements

Use async/await syntax

Use Promise with setTimeout()

adder(previous, next) This function accepts the previous number and the next number.
It resolves a Promise after 10ms to return the new total.

iterateNumbers(numbers) This function accepts an array of numbers to be totaled. 
It prints the total when all numbers have been added.

    iterateNumbers([1, 2, 3, 5, 8, 13, 21])
    // Total:
    // 53
*/

const adder = (previous, next) => {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            const sum = previous + next;
            if (isNaN(sum))
                reject(err)
            else
                resolve(sum)
        }, 10);
    });
}


async function iterateNumbers(numbers) {
    let total = 0;
    for(let i = 0; i < numbers.length; i++) {
        const sum = await adder(total, numbers[i]);
        total = sum
    }
    console.log(`Total:\n${total}\n\n`);
}

iterateNumbers([1, 2, 3, 5, 8, 13, 21]);

/*

2. Using callbacks syntax write a set of functions that checks and makes a list of todos based on priorities.
Requirements

Use callbacks

Use setTimeout()

checkPriority(todo) This function accepts a todo object. It checks if the todo has a priority associated to it. If it does then it callsback after 90ms with the priority object. If it does not then it callsback with an error and only the name.

makePriorityList(todos) This function accepts an array of objects. It prints the results ONLY when the entire todo array has been iterated. It should print each the priority array and the error array seperately.

EXTRA CREDIT (+10)

When printing inside makePriorityList(), print the array sorted by priority from highest to lowest.
*/
const todos = [{
    name: 'get coffee',
    priority: 9
},
{
    name: 'clean room',
    priority: null
},
{
    name: 'go to CS4220',
    priority: 4
},
{
    name: 'do homework before due date',
    priority: 8
}]

// Priority
//   [ { name: 'get coffee', priority: 90 },
//   { name: 'do homework before due date', priority: 80 },
//   { name: 'go to CS4220', priority: 40 } ]
// Missing Priority [ 'clean room' ]

const checkPriority = ({ name, priority }, callback) => {
    setTimeout(() => {
        const error = !priority ? name: null
        callback(error, { name, priority: priority*10 });
    }, 90)
}


const PrintResults = (Priority, MissingPriority) => {
    // sort based on priority key
    const sortedPriority = Priority.sort((a,b) =>  b.priority - a.priority);
    console.log("Priority\n");
    console.log(sortedPriority);
    console.log("\nMissing Priority\n");
    console.log(MissingPriority);
}



const makePriorityList = (todos) => {
    const Priority = []
    const MissingPriority = []
    let count = 0;
    for(let i = 0; i < todos.length; i++) {
        checkPriority(todos[i], (err, result) => {
            if(err) {
                MissingPriority.push(err);
            } else {
                Priority.push(result);
            }
            count ++;
            if(count === todos.length) {
                PrintResults(Priority, MissingPriority);
            }
        })
    }
}

makePriorityList(todos)
