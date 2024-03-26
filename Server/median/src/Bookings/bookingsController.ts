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
            console.log("Заказ удален",booking)
        } catch (error: any) {
            res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message });
        }
    }
}