import { Module } from '@nestjs/common';
import { ServiceController } from './service.controller';
import { JobService } from './service.service';
import { ArticlesModule } from '../articles/articles.module';

@Module({
  imports: [ArticlesModule],
  controllers: [ServiceController],
  providers: [JobService],
})

export class ServiceModule {}