import { Module } from '@nestjs/common';
import { UserController } from './usersController';
import { UserService } from './users.service';
import { ArticlesModule } from '../articles/articles.module';

@Module({
  imports: [ArticlesModule],
  controllers: [UserController],
  providers: [UserService],
})

export class UsersModule {}