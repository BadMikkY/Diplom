import { Injectable } from '@nestjs/common';
import { PrismaClient, WorkProgress } from '@prisma/client';



const prisma = new PrismaClient();

@Injectable()
export class ProgressService {

  async createWorkProgress(data: any) {
    const workProgress = await prisma.workProgress.create({
      data: {
        ...data,
      }
    });
    return workProgress;
  }

  async updateWorkProgress(progressId: number, comment: string): Promise<WorkProgress> {
    const workProgress = await prisma.workProgress.update({
      where: {
        ProgressID: progressId,
      },
      data: {
        Comment: comment,
      },
    });
    return workProgress;
  }

  async getProgressById(bookingId: number) {
    console.log('Searching for BookingID:', bookingId); // Логируем BookingID перед поиском
    const workProgress = await prisma.workProgress.findUnique({
      where: {
        BookingID: bookingId,
      }
    });
    return workProgress;
}

  async updatePercentOfProgress(progressId: number, persentage: number): Promise<WorkProgress> {
    const workProgress = await prisma.workProgress.update({
      where: {
        ProgressID: progressId,
      },
      data: {
        Percentage:persentage,
      },
    });
    return workProgress;
  }
}