export class TaskManager {
    private tasks: string[]= [];

    addTask(task: string){
        this.tasks.push(task);
    }
    getTasks(){
        return this.tasks;
    }
}

const manager = new TaskManager();
manager.addTask("comprar leche");
console.log(manager.getTasks());