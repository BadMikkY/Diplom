import { Injectable } from '@nestjs/common';
import * as bcrypt from 'bcrypt';
import { PrismaClient } from '@prisma/client';


const prisma = new PrismaClient();

@Injectable()
export class JobService {
    async createService(data: any) {
        const service = await prisma.service.create({
            data: {
                ...data,
            }
        });
        return service;
    }

    async deleteService(id: number) {
        const service = await prisma.service.delete({
            where: {
                ServiceID: id
            },
        });
        return service;
    }

    async getAllServices(serviceName?: string, description?: string) {
        let whereClause = {};
        if (serviceName) {
            whereClause = {
                ...whereClause,
                ServiceName: {
                    contains: serviceName,
                    mode: 'insensitive',
                },
            };
        }

        if (description) {
            whereClause = {
                ...whereClause,
                Description: {
                    contains: description,
                    mode: 'insensitive',
                },
            };
        }

        const service = await prisma.service.findMany({
            where: whereClause,
            select: {
                ServiceID: true,
                ServiceName: true,
                Description: true,
                UserID: true,
                SpecialistID: true,
            },
        });

        return service;
    }
}