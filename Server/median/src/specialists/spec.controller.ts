import { Body, Controller, Get, HttpStatus, Param, Post, Res } from '@nestjs/common';
import { SpecService } from './spec.service';
import { ApiTags } from '@nestjs/swagger';

@ApiTags('spec')
@Controller('spec')
export class SpecController {
  constructor(private readonly specService: SpecService) { }

  @Post('create')
  async createSpec(@Body() specData: any, @Res() res: any) {
    try {
      const newSpec = await this.specService.createSpec(specData);
      res.status(HttpStatus.OK).json(newSpec);
    } catch (error: any) {
      res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message });
    }
  }

  @Get(':id')
  async getSpec(@Param('id') id: number, @Res() res: any) {
    try {
      const spec = await this.specService.getSpec(id);
      res.status(HttpStatus.OK).json(spec);
    } catch (error: any) {
      res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message });
    }
  }

  @Post('login')
  async loginSpec(@Body() loginData: { email: string, password: string }, @Res() res: any) {
    try {
      const spec = await this.specService.loginSpec(loginData.email, loginData.password);
      if (spec) {
        spec.Password = undefined;
      }
      res.status(HttpStatus.OK).json(spec);
    } catch (error: any) {
      res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message });
    }
  }

  @Post('search')
  async searchSpec(@Body() searchData: { name: string, skills: string }, @Res() res: any) {
    if (typeof searchData.name === 'string' && typeof searchData.skills === 'string') {
      try {
        const specialists = await this.specService.findSpecialistsByNameAndSkills(searchData.name, searchData.skills);
        res.status(HttpStatus.OK).json(specialists);
      } catch (error: any) {
        res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message });
      }
    } else {
      res.status(HttpStatus.BAD_REQUEST).json({ error: 'Invalid body parameters. Both name and skills must be provided.' });
    }
  }

  @Post('reviews')
  async addReviewAndRating(@Body('UserID') UserID: number, @Body('SpecialistID') SpecialistID: number, @Body('ReviewText') ReviewText: string, @Body('Rating') Rating: string) {
    return this.specService.addReviewAndRating(UserID, SpecialistID, ReviewText, Rating);
  }

  @Post('getreviews')
  async getReviewsBySpecialistId(@Body('SpecialistID') specialistId: number, @Res() res: any) {
    try {
      const reviews = await this.specService.getReviewsBySpecialistId(specialistId);
      res.status(HttpStatus.OK).json(reviews);
    } catch (error: any) {
      res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message });
    }
  }

}