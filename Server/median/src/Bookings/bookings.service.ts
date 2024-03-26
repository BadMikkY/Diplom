import { Injectable } from '@nestjs/common';
import { PrismaClient } from '@prisma/client';



const prisma = new PrismaClient();

@Injectable()
export class BookingService {
    async createBooking(data: any) {
        const booking = await prisma.booking.create({
            data: {
                ...data,
            },
        });
        return booking;
    }


    async deleteBooking(id: number) {
        const booking = await prisma.booking.delete({
            where: {
                BookingID: id
            },
        });
        return booking;
    }
}