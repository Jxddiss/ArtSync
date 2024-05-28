import { Forum } from "../models/forum.model";
import { USERS } from './mock-user';

export const FORUMS: Forum[] = [
    new Forum(
        1,
        'Les abeilles', 
        'Moi je trouve les abeilles sont des créatures formidables!',
        '2020-01-01',
        true, 
        USERS[0]
    ),
    new Forum(
        2,
        'Les Lions', 
        'Moi que les lions sont des créatures assez impressionnantes mais aussi dangereuses!',
        '2020-01-01',
        true, 
        USERS[1]
    ),
    new Forum(
        3,
        'Les éléphants', 
        'Les éléphants sont des animaux très intelligents et très sociables! Ils sont également très protecteurs envers leur famille!',
        '2020-01-01',
        true, 
        USERS[2]
    ),
    new Forum(
        4,
        'Les singes', 
        'Les singes sont des animaux très intelligents et très sociables! Ils sont également très protecteurs envers leur famille!',
        '2020-01-01',
        true, 
        USERS[3]
    ),
    new Forum(
        5,
        'Les chats', 
        'Les chats sont des animaux très intelligents et très sociables! Ils sont également très protecteurs envers leur famille!',
        '2020-01-01',
        true, 
        USERS[4]
    ),
]