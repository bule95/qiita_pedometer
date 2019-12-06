package jp.les.kasa.sample.mykotlinapp.activity.logitem

import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.les.kasa.sample.mykotlinapp.data.ShareStatus
import jp.les.kasa.sample.mykotlinapp.data.StepCountLog
import java.util.*

typealias LogItemData = Pair<StepCountLog, ShareStatus>

/**
 * ログアイテム表示画面用のViewModel
 * @date 2019/06/19
 **/
class LogItemViewModel : ViewModel() {

    private val _selectDate = MutableLiveData<Calendar>()
    val selectDate = _selectDate as LiveData<Calendar>

    private val _deleteLog = MutableLiveData<StepCountLog>()
    val deleteLog = _deleteLog as LiveData<StepCountLog>

    private val _logItem = MutableLiveData<LogItemData>()
    val logItem = _logItem as LiveData<LogItemData>

    @UiThread
    fun changeLog(data: StepCountLog, shareStatus: ShareStatus) {
        _logItem.value = LogItemData(data, shareStatus)
    }

    @UiThread
    fun dateSelected(selectedDate: Calendar) {
        _selectDate.value = selectedDate
    }

    @UiThread
    fun deleteLog(data: StepCountLog) {
        _deleteLog.value = data
    }

    private val _selectShareSns = MutableLiveData<SNSType>()
    val selectShareSns = _selectShareSns as LiveData<SNSType>

    @UiThread
    fun selectShareSns(snsType: Int) {
        _selectShareSns.value = SNSType.values()[snsType]
    }
}

enum class SNSType {
    Twitter,
    Instagram,
}