import { Module } from '@nestjs/common';
import { SpecController } from './specialists/spec.controller';
import { SpecService } from './specialists/spec.service';
import { ArticlesModule } from './articles/articles.module';
import { UsersModule } from './users/users.module';
import { BookingModule } from './Bookings/bookings.module';
import { ProgressModule } from './WorkProgress/progress.module';
import {ServiceModule} from './Services/service.module'

@Module({
  imports: [ArticlesModule,UsersModule,BookingModule,ProgressModule,ServiceModule],
  controllers: [SpecController],
  providers: [SpecService],
})

export class AppModule {}
