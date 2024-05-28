import { File } from '../models/file.model';
import { USERS } from './mock-user';

export const FILES: File[] = [
    new File(
        1,
        '../../assets/images/retro.jpg',
        1,
        1,
        USERS[0],
        'image'
    ),
    new File(
        2,
        '../../assets/images/moderne.jpg',
        2,
        2,
        USERS[1],
        'image'
    ),
    new File(
        3,
        '../../assets/images/contemporain.jpg',
        3,
        3,
        USERS[2],
        'image'
    ),
    new File(
        4,
        '../../assets/images/abstrait.jpg',
        4,
        4,
        USERS[3],
        'image'
    ),
    new File(
        5,
        '../../assets/images/impressionniste.jpg',
        5,
        5,
        USERS[4],
        'image'
    )
]
