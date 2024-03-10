import { Module } from '@nestjs/common';
import { SpecController } from './specialists/spec.controller';
import { SpecService } from './specialists/spec.service';
import { ArticlesModule } from './articles/articles.module';
import { UsersModule } from './users/users.module';

@Module({
  imports: [ArticlesModule,UsersModule],
  controllers: [SpecController],
  providers: [SpecService],
})

export class AppModule {}
