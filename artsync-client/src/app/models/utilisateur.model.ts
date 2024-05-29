export class Utilisateur {
    constructor(
        public id: number,
        public pseudo:string,
        public prenom:string,
        public nom:string,
        public email:string,
        public photoUrl:string,
        public specialisation:string,
        public role:string,
        public active:boolean
    ){}
}
