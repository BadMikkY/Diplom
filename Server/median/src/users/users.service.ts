import { Injectable } from '@nestjs/common';
import * as bcrypt from 'bcrypt';
import { PrismaClient } from '@prisma/client';


const prisma = new PrismaClient();

@Injectable()
export class UserService {
    async createUser(data: any) {
        const hashedPassword = await bcrypt.hash(data.Password, 10);

        const user = await prisma.user.create({
            data: {
                ...data,
                Password: hashedPassword,
            },
        });
        return user;
    }

    async loginUser(email: string, password: string) {
        const user = await prisma.user.findUnique({
            where: {
                Email: email,
            }
        });
        if (!user) {
            throw new Error('Пользователь с такой почтой не найден');
        }
        const isPasswordValid = await bcrypt.compare(password, user.Password);

        if (!isPasswordValid) {
            throw new Error('Неверный пароль');
        }
        return user;
    }

    async getUser(id: number) {
        const user = await prisma.user.findUnique({
            where: {
                UserID: id,
            },
        });
        return user;
    }
}