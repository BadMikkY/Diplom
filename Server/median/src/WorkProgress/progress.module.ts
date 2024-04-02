import { Module } from '@nestjs/common';
import { ProgressController } from './progressController';
import { ProgressService } from './progress.service';
import { ArticlesModule } from '../articles/articles.module';


@Module({
    imports:[ArticlesModule],
    controllers:[ProgressController],
    providers:[ProgressService]
})


export class ProgressModule{}