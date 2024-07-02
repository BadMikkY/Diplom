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

    async getBookingBySpecID(id:number){
        const bookings = await prisma.booking.findMany({
            where: {
                SpecialistID: id
            },
        });
        return bookings;
    }

    async getBookingByUserID(id:number){
        const bookings = await prisma.booking.findMany({
            where: {
                UserID: id
            },
        });
        return bookings;
    }

        async updateBookingStatus(id: number, status: string) {
            const booking = await prisma.booking.update({
                where: {
                    BookingID: id
                },
                data: {
                    Status: status
                },
            });
            return booking;
        }
}