import { Body, Controller, Get, HttpStatus, Param, Post, Res } from '@nestjs/common';
import { ProgressService } from './progress.service';
import { log } from 'node:console';

@Controller('progress')
export class ProgressController {
    constructor(private readonly progressService: ProgressService) { }

    @Post('create')
    async createProgress(@Body() progressData: any, @Res() res: any) {
        try {
            const newProgress = await this.progressService.createWorkProgress(progressData);
            res.status(HttpStatus.OK).json(progressData)
        } catch (error: any) {
            res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message });
        }
    }

    @Post('addcomment')
    async updateWorkProgress(@Body() progressData: { ProgressID: number, Comment: string }, @Res() res: any) {
        try {
            const newProgress = await this.progressService.updateWorkProgress(progressData.ProgressID, progressData.Comment);
            res.status(HttpStatus.OK).json(progressData)
            console.log(newProgress)
            return newProgress;
        } catch (error: any) {
            res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message });
        }
    }

    @Post('updatepercentage')
    async updatePercentage(@Body() progressData: { ProgressID: number, Percentage:number }, @Res() res: any) {
        try {
            const newProgress = await this.progressService.updatePercentOfProgress(progressData.ProgressID, progressData.Percentage);
            res.status(HttpStatus.OK).json(progressData)
            console.log(newProgress)
            return newProgress;
        } catch (error: any) {
            res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message });
        }
    }
    

    @Post('getprogress')
    async getProgressById(@Body('BookingID') id: number, @Res() res: any) {
        console.log('Received BookingID:', id); // Логируем полученный BookingID
        try {
            const progress = await this.progressService.getProgressById(id);
            console.log('Progress:', progress); // Логируем полученный progress
            res.status(HttpStatus.OK).json(progress);
        } catch (error: any) {
            console.log('Error:', error.message); // Логируем ошибку
            res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message });
        }
    }
}