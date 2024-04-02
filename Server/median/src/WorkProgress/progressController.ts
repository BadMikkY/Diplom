import { Body, Controller, Get, HttpStatus, Param, Post, Res } from '@nestjs/common';
import { ProgressService } from './progress.service';

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
    async updateWorkProgress(@Body() progressData: { progressId: number, comment: string }, @Res() res: any) {
        try {
            const newProgress = await this.progressService.updateWorkProgress(progressData.progressId, progressData.comment);
            res.status(HttpStatus.OK).json(progressData)
            console.log(newProgress)
            return newProgress;
        } catch (error: any) {
            res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message });
        }
    }

    @Post('updatepercentage')
    async updatePercentage(@Body() progressData: { progressId: number, percentage:number }, @Res() res: any) {
        try {
            const newProgress = await this.progressService.updatePercentOfProgress(progressData.progressId, progressData.percentage);
            res.status(HttpStatus.OK).json(progressData)
            console.log(newProgress)
            return newProgress;
        } catch (error: any) {
            res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message });
        }
    }
    

    @Post('getprogress')
    async getProgressById(@Param('ProgressID') id: number, @Res() res: any) {
        try {
            const spec = await this.progressService.getProgressById(id);
            res.status(HttpStatus.OK).json(spec);
        } catch (error: any) {
            res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message });
        }
    }
}