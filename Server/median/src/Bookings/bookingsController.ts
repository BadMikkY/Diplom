import { Body, Controller, Get, HttpStatus, Param, Post, Res } from '@nestjs/common';
import { BookingService } from './bookings.service';
import { spec } from 'node:test/reporters';


@Controller('booking')
export class BookingController {
    constructor(private readonly bookingService: BookingService) { }


    @Post('create')
    async createBooking(@Body() bookingData: any, @Res() res: any) {
        try {
            const newbooking = await this.bookingService.createBooking(bookingData);
            res.status(HttpStatus.OK).json(newbooking)
        } catch (error: any) {
            res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message });
        }
    }


    @Post('remove')
    async deleteBooking(@Body('BookingID') id: number, @Res() res: any) {
        try {
            const booking = await this.bookingService.deleteBooking(id);
            res.status(HttpStatus.OK).json(booking)
            console.log("Заказ удален", booking)
        } catch (error: any) {
            res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message });
        }
    }


    @Post('getBySpecID')
    async getBookingBySpecID(@Body('SpecialistID') id: number, @Res() res: any) {
        try {
            const bookings = await this.bookingService.getBookingBySpecID(id);
            res.status(HttpStatus.OK).json(bookings)
        } catch (error: any) {
            res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message });
        }
    }

    @Post('getByUserID')
    async getBookingByUserID(@Body('UserID') id: number, @Res() res: any) {
        try {
            const bookings = await this.bookingService.getBookingByUserID(id);
            res.status(HttpStatus.OK).json(bookings)
        } catch (error: any) {
            res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message });
        }
    }


    @Post('updateStatus')
    async updateBookingStatus(@Body('BookingID') id: number, @Body('Status') status: string, @Res() res: any) {
        try {
            const booking = await this.bookingService.updateBookingStatus(id, status);
            res.status(HttpStatus.OK).json(booking)
            console.log("Статус заказа обновлен", booking)
        } catch (error: any) {
            res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message });
        }
    }
}