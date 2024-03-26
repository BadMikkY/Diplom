import { Module } from '@nestjs/common';
import { BookingController } from './bookingsController';
import { BookingService } from './bookings.service';
import { ArticlesModule } from '../articles/articles.module';


@Module({
    imports:[ArticlesModule],
    controllers: [BookingController],
    providers:[BookingService]
})

export class BookingModule{}