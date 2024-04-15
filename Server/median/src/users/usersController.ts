import { Body, Controller, Get, HttpStatus, Param, Post, Res } from '@nestjs/common';
import { UserService } from './users.service';
import { ApiTags } from '@nestjs/swagger';


@ApiTags('user')
@Controller('user')
export class UserController {
    constructor(private readonly userService: UserService) { }
    @Post('create')
    async createUser(@Body() userData: any, @Res() res: any) {
        try {
            const newUser = await this.userService.createUser(userData);
            res.status(HttpStatus.OK).json(newUser);
        } catch (error: any) {
            res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message })
        }
    }

    @Get(':id')
    async getUser(@Param('id') id: number, @Res() res: any) {
        try {
            const UserID = Number(id);
            const user = await this.userService.getUser(UserID);
            res.status(HttpStatus.OK).json(user)
        } catch (error: any) {
            res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message });
        }
    }

    @Post('login')
    async loginUser(@Body() loginData: { Email: string, Password: string }, @Res() res: any) {
        try {
            let user = await this.userService.loginUser(loginData.Email, loginData.Password);
            if (user) {
                user.Password = undefined; 
            }
            res.status(HttpStatus.OK).json(user);
        } catch (error: any) {
            res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({ error: error.message });
        }
    }
}