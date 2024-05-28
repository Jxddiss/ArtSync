export class Forum{
    constructor(
        public id: number,
        public title: string,
        public content: string,
        public date: string,
        public publique: boolean,
        public userId : number,
    ) {}
}