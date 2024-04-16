import { Body, Controller, Get, HttpStatus, Param, Post, Res } from '@nestjs/common';
import { JobService } from './service.service';
import { ApiTags } from '@nestjs/swagger';


@ApiTags('service')
@Controller('service')
export class ServiceController {
    constructor(private readonly jobService: JobService) { }
    @Post('create')
    async createService(@Body() serviceData: any, @Res() res: any) {
        try {
            const newService = await this.jobService.createService(serviceData);
            res.status(HttpStatus.OK).json(newService);
        } catch (error: any) {
            res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message })
        }
    }

    @Post('remove')
    async deleteService(@Body('ServiceID') id: number, @Res() res: any) {
        try {
            const service = await this.jobService.deleteService(id);
            res.status(HttpStatus.OK).json(service)
            console.log("Сервис удален", service)
        } catch (error: any) {
            res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({error:error.message})
        }
    }

    @Post('search')
    async searchService(@Body() searchData: { serviceName: string, description: string }, @Res() res: any) {
      if (typeof searchData.serviceName === 'string' && typeof searchData.description === 'string') {
        try {
          const service= await this.jobService.getAllServices(searchData.serviceName, searchData.description);
          res.status(HttpStatus.OK).json(service);
        } catch (error: any) {
          res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message });
        }
      } else {
        res.status(HttpStatus.BAD_REQUEST).json({ error: 'Invalid body parameters. Both serviceName and description must be provided.' });
      }
    }


    @Get('all')
    async getAllServices(@Res() res: any) {
        try {
            const services = await this.jobService.getAllServices();
            res.status(HttpStatus.OK).json(services);
        } catch (error: any) {
            res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message });
        }
    }
}