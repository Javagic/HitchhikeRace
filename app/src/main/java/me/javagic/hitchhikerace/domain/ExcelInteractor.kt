package me.javagic.hitchhikerace.domain

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Environment
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import com.fasterxml.jackson.module.kotlin.readValue
import io.reactivex.android.schedulers.AndroidSchedulers
import me.javagic.hitchhikerace.R
import me.javagic.hitchhikerace.app.RaceApplication.Companion.mapper
import me.javagic.hitchhikerace.data.database.entity.RaceEventEntity
import me.javagic.hitchhikerace.data.pojo.RaceEventType
import me.javagic.hitchhikerace.utils.tryOrNull
import me.javagic.hitchhikerace.view.eventcreation.takecheckpoint.CheckPointCrew
import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD
import org.apache.poi.ss.usermodel.IndexedColors
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


const val SHIFT = "\n"

fun RaceEventEntity.takeCheckpointText(): String {
    val result = mainText + SHIFT + eventDescription
    val text =
        tryOrNull { mapper.readValue<List<CheckPointCrew>>(specialDataText) }?.takeIf { it.isNotEmpty() }
            ?.joinToString(separator = "\n") { it.toString() }?.takeIf { it != "null" }
    return result + (text?.takeIf { it != "null" }?.let { "\n$text" } ?: "")
}

class ExcelInteractor @Inject constructor(private val eventInteractor: RaceEventInteractor) {
    var dateFormat = "dd.MM.yyyy"
    private val COLUMNs = arrayOf(
        "№",
        "Дата",
        "Время",
        "Событие",
        "Машина, Место, Комментарий",
        "Потрачено Rest"
    )
    private val filePath: File =
        File(Environment.getExternalStorageDirectory().toString() + "/RaceApp.xls")

    private lateinit var shareFilePath: File

    @SuppressLint("CheckResult")
    fun processFile(context: Context, raceId: Long) {
        val workbook = HSSFWorkbook()
        val createHelper = workbook.creationHelper

        val sheet = workbook.createSheet("Customers")
        val headerFont = workbook.createFont()
        headerFont.boldweight = BOLDWEIGHT_BOLD

        val headerCellStyle = workbook.createCellStyle()
        headerCellStyle.setFont(headerFont)

        headerCellStyle.fillPattern = HSSFCellStyle.SOLID_FOREGROUND
        headerCellStyle.fillForegroundColor = IndexedColors.LIGHT_GREEN.getIndex()
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
        var technicalText = ""
        eventInteractor.getAllRaceEventList(raceId)
            .observeOn(AndroidSchedulers.mainThread())
            .firstElement()
            .subscribe({ list ->
                var rowIdx = 1
                var carIndex = 1
                for (event in list) {
                    val row = sheet.createRow(rowIdx++)
                    if (event.raceEventType == RaceEventType.CAR_START) {
                        row.createCell(0).setCellValue(carIndex.toString())
                        carIndex++
                    }
                    val calendar: Calendar = Calendar.getInstance()
                    calendar.timeInMillis = event.realTime
                    SimpleDateFormat(dateFormat).format(calendar.time)?.let {
                        row.createCell(1).setCellValue(it)
                    }
                    row.createCell(2).setCellValue(event.timeString)
                    row.createCell(3).setCellValue(getEventTypeTitle(context, event.raceEventType))
                    val commentCell = row.createCell(4)
                    if (event.raceEventType == RaceEventType.TAKE_CHECKPOINT) {
                        commentCell.setCellValue(event.takeCheckpointText())
                    } else {
                        commentCell.setCellValue(event.mainText + SHIFT + event.eventDescription + SHIFT + event.specialDataText)
                    }
                    if (event.raceEventType == RaceEventType.REST_FINISH) {
                        row.createCell(5).setCellValue(event.currentRest)
                    }
                    if (event.technicalText.isNotEmpty()) {
                        technicalText += "Строка $rowIdx:\n" + event.technicalText + "\n"
                    }
                }
                val rw = sheet.getRow(0)
                val technicalRow = sheet.createRow(rowIdx)
                technicalRow.createCell(rw.lastCellNum).setCellValue(technicalText)
                (3 until rw.lastCellNum).forEach { colNum ->
                    val columnWidth = (rw.getCell(colNum).toString().length * 0.44 + 2.24) * 768
                    sheet.setColumnWidth(colNum, columnWidth.toInt())
                }
                try {
                    if (!filePath.exists()) {
                        filePath.createNewFile()
                    }
                    val fileOutputStream = FileOutputStream(filePath)
                    workbook.write(fileOutputStream)
                    fileOutputStream.flush()
                    fileOutputStream.close()

                    val dirName = "${context.filesDir.absolutePath}/external_files"
                    val directory = File(dirName)
                    if (!directory.exists()) {
                        directory.mkdir()
                    }
                    shareFilePath = File(dirName, "RaceApp.xls")
                    if (!shareFilePath.exists()) {
                        shareFilePath.createNewFile()
                    }
                    val sharefileOutputStream = FileOutputStream(shareFilePath)
                    workbook.write(sharefileOutputStream)
                    sharefileOutputStream.flush()
                    sharefileOutputStream.close()
                    if (shareFilePath.exists()) {
                        val intentShareFile = Intent(Intent.ACTION_SEND)
                        val uri = FileProvider.getUriForFile(
                            context,
                            context.applicationContext.packageName.toString() + ".provider",
                            shareFilePath
                        )
                        intentShareFile.type = "application/xls"
                        intentShareFile.putExtra(Intent.EXTRA_STREAM, uri)
                        intentShareFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        val j =
                            Intent.createChooser(
                                intentShareFile,
                                "Choose an application to open with:"
                            )
                        startActivity(context, j, null)
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