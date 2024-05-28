import { Group } from '../models/group.model';
import { USERS } from './mock-user';

export const GROUPS: Group[] = [
    new Group(
        1,
        'Art rétro',
        'Groupe pour les amateurs de l\'art rétro',
        '../../assets/images/retro.jpg',
        true,
        '2020-01-01',
        '2020-01-01',
        '2020-01-01',
        USERS[0],
        5
    ),
    new Group(
        2,
        'Art moderne',
        'Groupe pour les amateurs de l\'art moderne',
        '../../assets/images/moderne.jpg',
        true,
        '2020-01-01',
        '2020-01-01',
        '2020-01-01',
        USERS[1],
        8
    ),
    new Group(
        3,
        'Art contemporain',
        'Groupe pour les amateurs de l\'art contemporain',
        '../../assets/images/contemporain.jpg',
        true,
        '2020-01-01',
        '2020-01-01',
        '2020-01-01',
        USERS[2],
        2
    ),
    new Group(
        4,
        'Art abstrait',
        'Groupe pour les amateurs de l\'art abstrait',
        '../../assets/images/abstrait.jpg',
        true,
        '2020-01-01',
        '2020-01-01',
        '2020-01-01',
        USERS[3],
        4
    ),
    new Group(
        5,
        'Art impressionniste',
        'Groupe pour les amateurs de l\'art impressionniste',
        '../../assets/images/impressionniste.jpg',
        true,
        '2020-01-01',
        '2020-01-01',
        '2020-01-01',
        USERS[4],
        4
    )]