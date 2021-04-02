package me.javagic.hitchhikerace.domain

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.core.content.ContextCompat.startActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import me.javagic.hitchhikerace.R
import me.javagic.hitchhikerace.data.PreferenceManager
import me.javagic.hitchhikerace.data.pojo.RaceEventType
import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD
import org.apache.poi.ss.usermodel.IndexedColors
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


class ExelInteractor @Inject constructor(private val eventInteractor: RaceEventInteractor) {
    private val COLUMNs = arrayOf(
        "№",
        "Дата",
        "Время",
        "Событие",
        "Машина, Место, Комментарий",
        "Потрачено Rest",
        "",
        ""
    )
    private val filePath: File =
        File(Environment.getExternalStorageDirectory().toString() + "/Demo.xls")

    @SuppressLint("CheckResult")
    fun processFile(context: Context) {
        val workbook = HSSFWorkbook()
        val createHelper = workbook.creationHelper

        val sheet = workbook.createSheet("Customers")

        val headerFont = workbook.createFont()
        headerFont.boldweight = BOLDWEIGHT_BOLD

        val headerCellStyle = workbook.createCellStyle()
        headerCellStyle.setFont(headerFont)

        headerCellStyle.fillPattern = HSSFCellStyle.SOLID_FOREGROUND
        headerCellStyle.fillForegroundColor = IndexedColors.LIGHT_GREEN.getIndex()

        // Row for Header
        val headerRow = sheet.createRow(0)

        // Header
        for (col in COLUMNs.indices) {
            val cell = headerRow.createCell(col)
            cell.setCellValue(COLUMNs[col])
            cell.setCellStyle(headerCellStyle)
        }

        // CellStyle for Age
        val ageCellStyle = workbook.createCellStyle()
        ageCellStyle.dataFormat = createHelper.createDataFormat().getFormat("#")

        eventInteractor.getAllRaceEventList(PreferenceManager().getCurrentRace())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                var rowIdx = 1
                var carIndex = 1
                for (event in list) {
                    val row = sheet.createRow(rowIdx++)
                    if (event.raceEventType == RaceEventType.CAR_START) {
                        row.createCell(0).setCellValue(carIndex.toString())
                        carIndex++
                    }
                    row.createCell(1).setCellValue(event.timeString)
                    row.createCell(2).setCellValue(event.timeString)
                    row.createCell(3).setCellValue(getEventTypeTitle(context, event.raceEventType))
                    row.createCell(4).setCellValue(event.mainText + event.eventDescription)
                    if (event.raceEventType == RaceEventType.REST_FINISH) {
                        row.createCell(5).setCellValue(event.currentRest)
                    }
                }
                try {
                    if (!filePath.exists()) {
                        filePath.createNewFile()
                    }
                    val fileOutputStream = FileOutputStream(filePath)
                    workbook.write(fileOutputStream)
                    fileOutputStream.flush()
                    fileOutputStream.close()
                    if (filePath.exists()) {
                        val intentShareFile = Intent(Intent.ACTION_SEND)
                        intentShareFile.type = "application/pdf";
                        intentShareFile.putExtra(
                            Intent.EXTRA_STREAM,
                            Uri.parse("file://" + filePath.path)
                        )

                        intentShareFile.putExtra(
                            Intent.EXTRA_SUBJECT,
                            "Sharing File..."
                        )
                        intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");

                        startActivity(
                            context,
                            Intent.createChooser(intentShareFile, "Share File"),
                            null
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, {})

    }

    fun getEventTypeTitle(context: Context, type: RaceEventType) = when (type) {
        RaceEventType.RACE_START -> context.getString(R.string.start)
        RaceEventType.RACE_FINISH -> context.getString(R.string.finish)
        RaceEventType.CAR_START -> context.getString(R.string.car_start_title)
        RaceEventType.CAR_FINISH -> context.getString(R.string.car_finish_title)
        RaceEventType.RUN_START -> context.getString(R.string.start_run)
        RaceEventType.RUN_FINISH -> context.getString(R.string.finish_run)
        RaceEventType.ORIENTATION_START -> context.getString(R.string.orientation_start)
        RaceEventType.ORIENTATION_FINISH -> context.getString(R.string.orientation_finish)
        RaceEventType.CREW_MEETING -> context.getString(R.string.meeting)
        RaceEventType.REST_START -> context.getString(R.string.rest_eng)
        RaceEventType.REST_FINISH -> context.getString(R.string.rest_finish_eng)
        RaceEventType.TAKE_CHECKPOINT -> context.getString(R.string.take_checkpoint)
        RaceEventType.CUSTOM -> context.getString(R.string.other)
    }
}