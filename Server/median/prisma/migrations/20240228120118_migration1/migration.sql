/*
  Warnings:

  - A unique constraint covering the columns `[Email]` on the table `Specialist` will be added. If there are existing duplicate values, this will fail.
  - Added the required column `Email` to the `Specialist` table without a default value. This is not possible if the table is not empty.
  - Added the required column `Password` to the `Specialist` table without a default value. This is not possible if the table is not empty.
  - Added the required column `SpecName` to the `Specialist` table without a default value. This is not possible if the table is not empty.

*/
-- AlterTable
ALTER TABLE "Specialist" ADD COLUMN     "Email" TEXT NOT NULL,
ADD COLUMN     "Password" TEXT NOT NULL,
ADD COLUMN     "SpecName" TEXT NOT NULL;

-- CreateIndex
CREATE UNIQUE INDEX "Specialist_Email_key" ON "Specialist"("Email");
