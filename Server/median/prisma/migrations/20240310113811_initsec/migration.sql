/*
  Warnings:

  - Added the required column `UserID` to the `Service` table without a default value. This is not possible if the table is not empty.

*/
-- AlterTable
ALTER TABLE "Service" ADD COLUMN     "SpecialistID" INTEGER,
ADD COLUMN     "UserID" INTEGER NOT NULL;

-- AddForeignKey
ALTER TABLE "Service" ADD CONSTRAINT "Service_UserID_fkey" FOREIGN KEY ("UserID") REFERENCES "User"("UserID") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "Service" ADD CONSTRAINT "Service_SpecialistID_fkey" FOREIGN KEY ("SpecialistID") REFERENCES "Specialist"("SpecialistID") ON DELETE SET NULL ON UPDATE CASCADE;
